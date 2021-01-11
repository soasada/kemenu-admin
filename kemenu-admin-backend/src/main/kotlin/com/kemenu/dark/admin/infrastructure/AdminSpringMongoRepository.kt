package com.kemenu.dark.admin.infrastructure

import com.kemenu.dark.admin.domain.Admin
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface AdminSpringMongoRepository : ReactiveMongoRepository<Admin, String> {
    fun findAdminByEmail(email: String): Mono<Admin>
}