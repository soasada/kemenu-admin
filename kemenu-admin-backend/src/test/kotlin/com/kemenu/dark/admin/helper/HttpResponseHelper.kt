package com.kemenu.dark.admin.helper

import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.ClientResponse
import reactor.core.publisher.Mono

object HttpResponseHelper {

    fun json(jsonResponse: String): Mono<ClientResponse> {
        return Mono.just(
                ClientResponse.create(HttpStatus.OK)
                        .header("content-type", "application/json")
                        .body(jsonResponse)
                        .build()
        )
    }
}