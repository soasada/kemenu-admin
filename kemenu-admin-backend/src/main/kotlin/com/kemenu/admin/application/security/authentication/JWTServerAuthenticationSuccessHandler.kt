package com.kemenu.admin.application.security.authentication

import com.kemenu.admin.application.HttpExceptionFactory.unauthorized
import com.kemenu.admin.application.security.JWTService
import com.kemenu.admin.application.utils.logger
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JWTServerAuthenticationSuccessHandler(private val jwtService: JWTService) : ServerAuthenticationSuccessHandler {

    val logger = logger()

    private val FIFTEEN_MIN = 1000 * 60 * 15
    private val FOUR_HOURS = 1000 * 60 * 60 * 4

    override fun onAuthenticationSuccess(webFilterExchange: WebFilterExchange?, authentication: Authentication?): Mono<Void> = mono {
        val principal = authentication?.principal ?: throw unauthorized()

        when(principal) {
            is User -> {
                logger.info("User {} logs in", principal.username)
                val roles = principal.authorities.map { it.authority }.toTypedArray()
                val accessToken = jwtService.accessToken(principal.username, FIFTEEN_MIN, roles)
                val refreshToken = jwtService.refreshToken(principal.username, FOUR_HOURS, roles)
                webFilterExchange?.exchange?.response?.headers?.set("Authorization", accessToken)
                webFilterExchange?.exchange?.response?.headers?.set("JWT-Refresh-Token", refreshToken)
            }
        }

        return@mono null
    }
}