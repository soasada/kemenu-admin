package com.kemenu.admin.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import java.time.Instant

@Document
data class Admin(
        @Id val id: String,
        @Indexed(unique = true) val email: String,
        val password: String,
        val role: Role,
        @CreatedDate val createdAt: Instant = Instant.now(),
        @LastModifiedDate val updatedAt: Instant? = Instant.now(),
) : GrantedAuthority {

    override fun getAuthority(): String {
        return "ROLE_$role"
    }

    override fun equals(other: Any?) = other is Admin && EssentialAdminData(this) == EssentialAdminData(other)
    override fun hashCode() = EssentialAdminData(this).hashCode()

    private data class EssentialAdminData(val id: String) {
        constructor(admin: Admin) : this(admin.id)
    }

    enum class Role {
        SUPER_ADMIN, ADMIN, MODERATOR
    }
}