package com.kemenu.dark.admin.application.blog

import com.kemenu.dark.admin.domain.BlogPost
import com.kemenu.dark.admin.domain.Post
import org.bson.types.ObjectId

object BlogPostMapper {

    fun toNewEntity(post: Post, request: BlogPostRequest): BlogPost {
        return BlogPost(ObjectId().toString(), request.imageUrl, mapOf(Pair(post.locale, post)))
    }

    fun toUpdateEntity(old: BlogPost, request: BlogPostRequest): BlogPost {
        return BlogPost(old.id, request.imageUrl, old.posts, old.createdAt)
    }
}