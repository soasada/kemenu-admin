package com.kemenu.dark.admin.application.security.authentication

import com.kemenu.dark.admin.application.HttpExceptionFactory.badRequest
import com.kemenu.dark.admin.application.HttpExceptionFactory.unauthorized
import com.kemenu.dark.admin.application.login.LoginRequest
import com.kemenu.dark.admin.application.security.recaptcha.RecaptchaService
import com.kemenu.dark.admin.application.utils.JsonUtils
import com.kemenu.dark.admin.application.utils.logger
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import javax.validation.Validator

@Component
class JWTConverter(
        private val jsonUtils: JsonUtils,
        private val validator: Validator,
        private val recaptchaService: RecaptchaService,
) : ServerAuthenticationConverter {

    val logger = logger()

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> = mono {
        val loginRequest = getUsernameAndPassword(exchange!!) ?: throw badRequest()

        if (validator.validate(loginRequest).isNotEmpty()) {
            throw badRequest()
        }

        val validRecaptcha = recaptchaService.valid(loginRequest.recaptchaToken).awaitFirstOrNull() ?: throw badRequest()

        if (!validRecaptcha) {
            logger.warn("Someone is trying to log with a wrong recaptcha username: {}", loginRequest.username)
            throw unauthorized()
        }

        return@mono UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
    }

    private suspend fun getUsernameAndPassword(exchange: ServerWebExchange): LoginRequest? {
        val dataBuffer = exchange.request.body
        return jsonUtils.deserialize(dataBuffer, LoginRequest::class.java).awaitFirstOrNull()
    }
}
