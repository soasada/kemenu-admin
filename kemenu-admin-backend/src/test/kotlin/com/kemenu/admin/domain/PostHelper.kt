package com.kemenu.admin.domain

import com.kemenu.admin.helper.WordHelper
import java.util.UUID

object PostHelper {

    fun random() = random(randomLocale())

    fun random(locale: String): Post {
        val title = randomTitle()
        return Post(
                Post.createReadableId(title),
                title,
                UUID.randomUUID().toString(),
                locale,
                "admin",
                "admin"
        )
    }

    fun randomTitle() = WordHelper.random() + ' ' + WordHelper.random() + ' ' + WordHelper.random()
    fun randomLocale() = arrayOf(
            "en", "es", "fr", "de", "pt", "ch", "al", "ax", "dz", "ag", "bb", "by", "fo", "gl"
    ).random()
}