package com.kemenu.admin.application.security.recaptcha

import com.kemenu.admin.application.utils.JsonUtils
import com.kemenu.admin.application.utils.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters.fromMultipartData
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class RecaptchaService(
        private val webClient: WebClient,
        @Value("\${app.recaptcha.secret}") val recaptchaSecret: String,
        private val jsonUtils: JsonUtils
) {

    val logger = logger()

    fun valid(recaptchaToken: String): Mono<Boolean> {
        return verifyRecaptcha(recaptchaToken)
                .flatMap { jsonUtils.deserialize(it, RecaptchaResponse::class.java) }
                .flatMap { Mono.just(it.success) }
                .switchIfEmpty {
                    logger.error("Error while deserializing recaptcha response")
                    Mono.just(false)
                }
    }

    private fun verifyRecaptcha(recaptchaToken: String): Mono<String> {
        return webClient.post()
                .uri("https://www.google.com/recaptcha/api/siteverify")
                .body(fromMultipartData("secret", recaptchaSecret).with("response", recaptchaToken))
                .retrieve()
                .bodyToMono(String::class.java)
    }
}