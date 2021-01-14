package com.kemenu.admin.domain

import com.kemenu.admin.application.blog.BlogPostRequest
import org.bson.types.ObjectId
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

object BlogPostHelper {

    fun random(): BlogPost {
        return BlogPost(
                ObjectId().toString(),
                "https://example.com",
                mapOf()
        )
    }

    fun random(post: Post): BlogPost {
        return BlogPost(
                ObjectId().toString(),
                "https://example.com",
                mapOf(Pair(post.locale, post))
        )
    }

    fun request(): BlogPostRequest {
        return BlogPostRequest(
                PostHelper.randomTitle(),
                UUID.randomUUID().toString(),
                "https://example.com",
                PostHelper.randomLocale()
        )
    }

    fun request(locale: String): BlogPostRequest {
        return BlogPostRequest(
                PostHelper.randomTitle(),
                UUID.randomUUID().toString(),
                "https://example.com",
                locale
        )
    }

    fun find(webTestClient: WebTestClient, token: String, id: String) = webTestClient
            .get().uri("/v1/blog/${id}")
            .header(HttpHeaders.AUTHORIZATION, token)
            .exchange()
            .expectStatus().isOk
            .expectBody(BlogPost::class.java)
            .returnResult()
            .responseBody
}