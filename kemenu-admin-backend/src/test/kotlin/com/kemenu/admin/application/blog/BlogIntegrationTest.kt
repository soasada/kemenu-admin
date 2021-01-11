package com.kemenu.admin.application.blog

import com.kemenu.admin.KemenuDarkIntegrationTest
import com.kemenu.admin.domain.BlogPost
import com.kemenu.admin.domain.BlogPostHelper
import com.kemenu.admin.domain.PostHelper
import com.kemenu.admin.infrastructure.BlogPostSpringMongoRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Mono

class BlogIntegrationTest(
        @Autowired private val repository: BlogPostSpringMongoRepository
) : KemenuDarkIntegrationTest() {

    @Test
    fun `Should return all the blog posts`() {
        val post = BlogPostHelper.random()
        repository.save(post).block()

        webTestClient
                .get().uri("/v1/blog")
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk
                .expectBodyList<BlogPost>()
                .contains(post)
    }

    @Test
    fun `Should create a new post`() {
        val request = BlogPostHelper.request()

        val createdPost = webTestClient
                .post().uri("/v1/blog")
                .body(Mono.just(request), BlogPostRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk
                .expectBody(BlogPost::class.java)
                .returnResult()
                .responseBody

        val savedPost = repository.findById(createdPost!!.id).block()
        assertEquals(createdPost, savedPost)
    }

    @Test
    fun `Should return a specific blog post`() {
        val post = BlogPostHelper.random()
        repository.save(post).block()

        val blogPost = BlogPostHelper.find(webTestClient, accessToken(), post.id)

        assertEquals(post, blogPost)
    }

    @Test
    fun `Should return NOT FOUND if try to find a non existing blog post`() {
        webTestClient
                .get().uri("/v1/blog/${ObjectId()}")
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isNotFound
    }

    @Test
    fun `Should update a post with different user and different readable id`() {
        val locale = "en"
        val post = PostHelper.random(locale)
        val blog = BlogPostHelper.random(post)
        repository.save(blog).block()
        val request = BlogPostHelper.request(locale)

        val beforeUpdate = BlogPostHelper.find(webTestClient, accessToken(), blog.id)

        webTestClient
                .put().uri("/v1/blog/${blog.id}")
                .body(Mono.just(request), BlogPostRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk
                .expectBody(BlogPost::class.java)
                .returnResult()
                .responseBody

        val afterUpdate = BlogPostHelper.find(webTestClient, accessToken(), blog.id)

        assertEquals(beforeUpdate!!, afterUpdate!!)
        assertNotEquals(beforeUpdate.getPost(locale)?.readableId, afterUpdate.getPost(locale)?.readableId)
        assertNotEquals(beforeUpdate.getPost(locale)?.createdBy, afterUpdate.getPost(locale)?.updatedBy)
        assertEquals(beforeUpdate.getPost(locale)?.createdBy, afterUpdate.getPost(locale)?.createdBy)
        assertEquals(beforeUpdate.createdAt, afterUpdate.createdAt)
        assertEquals(beforeUpdate.getPost(locale)?.createdAt, afterUpdate.getPost(locale)?.createdAt)
    }

    @Test
    fun `Should return NOT FOUND if we try to update an unknown post`() {
        val post = PostHelper.random("en")
        val blog = BlogPostHelper.random(post)
        repository.save(blog).block()
        val request = BlogPostHelper.request("es")

        webTestClient
                .put().uri("/v1/blog/${blog.id}")
                .body(Mono.just(request), BlogPostRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isNotFound

        val afterUpdate = BlogPostHelper.find(webTestClient, accessToken(), blog.id)
        assertNull(afterUpdate?.getPost("es"))
    }

    @Test
    fun `Should delete a blog post`() {
        val post = BlogPostHelper.random()
        repository.save(post).block()

        webTestClient
                .delete().uri("/v1/blog/${post.id}")
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk
    }
}