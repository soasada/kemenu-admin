package com.kemenu.admin.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BlogPostTest {
    @Test
    fun `Should add all posts with different locales`() {
        val blogPost = BlogPostHelper.random()
        val post = PostHelper.random("en")
        val post2 = PostHelper.random("es")

        val blogPost1 = blogPost.addPost(post).addPost(post2)

        assertEquals(2, blogPost1.posts.size)
    }

    @Test
    fun `Should add only one post per locale`() {
        val blogPost = BlogPostHelper.random()
        val post = PostHelper.random("en")
        val post2 = PostHelper.random("en")

        val blogPost1 = blogPost.addPost(post).addPost(post2)

        assertEquals(1, blogPost1.posts.size)
    }
}