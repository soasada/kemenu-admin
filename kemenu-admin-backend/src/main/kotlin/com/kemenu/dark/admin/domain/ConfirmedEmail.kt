package com.kemenu.dark.admin.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class ConfirmedEmail(
        @Id val id: String,
        @Indexed(unique = true) val email: String,
        val confirmed: Boolean,
        val expiration: Instant,
        @CreatedDate val createdAt: Instant = Instant.now(),
        @LastModifiedDate val updatedAt: Instant = Instant.now(),
) {

    override fun equals(other: Any?) = other is ConfirmedEmail && EssentialConfirmedEmailData(this) == EssentialConfirmedEmailData(other)
    override fun hashCode() = EssentialConfirmedEmailData(this).hashCode()

    private data class EssentialConfirmedEmailData(val id: String) {
        constructor(confirmedEmail: ConfirmedEmail) : this(confirmedEmail.id)
    }
}