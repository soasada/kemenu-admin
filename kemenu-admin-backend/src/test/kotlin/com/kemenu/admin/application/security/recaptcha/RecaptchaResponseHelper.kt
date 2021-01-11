package com.kemenu.admin.application.security.recaptcha

import com.kemenu.admin.helper.HttpResponseHelper
import org.springframework.web.reactive.function.client.ClientResponse
import reactor.core.publisher.Mono

object RecaptchaResponseHelper {

    fun ok(): Mono<ClientResponse> {
        return create("""{"success": true, "challenge_ts": "2020-09-18T18:00:00.123Z", "hostname": "localhost"}""")
    }

    fun create(jsonString: String): Mono<ClientResponse> {
        return HttpResponseHelper.json(jsonString)
    }
}