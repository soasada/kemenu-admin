package com.kemenu.admin.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("blogPosts")
data class BlogPost(
        @Id val id: String,
        val imageUrl: String,
        val posts: Map<String, Post>,
        @CreatedDate val createdAt: Instant = Instant.now(),
        @LastModifiedDate val updatedAt: Instant? = null,
) {
    fun addPost(post: Post) = this.copy(posts = posts + Pair(post.locale, post))

    fun getPost(locale: String) = posts[locale]

    override fun equals(other: Any?) = other is BlogPost && BlogPostId(this) == BlogPostId(other)
    override fun hashCode() = BlogPostId(this).hashCode()

    private data class BlogPostId(val id: String) {
        constructor(blogPost: BlogPost) : this(blogPost.id)
    }
}