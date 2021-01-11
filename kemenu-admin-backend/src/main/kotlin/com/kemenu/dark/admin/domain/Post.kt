package com.kemenu.dark.admin.domain

import org.apache.commons.lang3.StringUtils
import java.time.Instant

data class Post(
        val readableId: String,
        val title: String,
        val content: String,
        val locale: String,
        val createdBy: String,
        val updatedBy: String,
        val createdAt: Instant = Instant.now(),
        val updatedAt: Instant = Instant.now()
) {
    companion object {
        fun createReadableId(title: String) = StringUtils.stripAccents(title).trim().toLowerCase().replace(" ", "-")
    }

    override fun equals(other: Any?) = other is Post && PostId(this) == PostId(other)
    override fun hashCode() = PostId(this).hashCode()

    private data class PostId(val locale: String) {
        constructor(post: Post) : this(post.locale)
    }
}