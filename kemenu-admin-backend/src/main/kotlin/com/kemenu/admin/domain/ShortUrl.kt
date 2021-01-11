package com.kemenu.admin.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class ShortUrl(
        @Id val id: String,
        @Indexed val customerEmail: String,
        val businessId: String,
        val menus: List<Menu>,
        @CreatedDate val createdAt: Instant = Instant.now(),
        @LastModifiedDate val updatedAt: Instant = Instant.now(),
) {
    override fun equals(other: Any?) = other is ShortUrl && EssentialShortUrlData(this) == EssentialShortUrlData(other)
    override fun hashCode() = EssentialShortUrlData(this).hashCode()

    private data class EssentialShortUrlData(val id: String) {
        constructor(shortUrl: ShortUrl) : this(shortUrl.id)
    }
}