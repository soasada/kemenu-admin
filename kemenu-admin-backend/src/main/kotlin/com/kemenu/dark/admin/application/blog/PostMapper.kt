package com.kemenu.dark.admin.application.blog

import com.kemenu.dark.admin.domain.Post

object PostMapper {

    fun toNewEntity(createdBy: String, request: BlogPostRequest): Post {
        return Post(
                Post.createReadableId(request.title),
                request.title,
                request.content,
                request.locale,
                createdBy,
                createdBy
        )
    }

    fun toUpdateEntity(old: Post, updatedBy: String, request: BlogPostRequest): Post {
        return Post(
                Post.createReadableId(request.title),
                request.title,
                request.content,
                request.locale,
                old.createdBy,
                updatedBy,
                old.createdAt
        )
    }
}