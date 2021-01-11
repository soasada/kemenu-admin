package com.kemenu.dark.admin.application.security.authentication

import com.kemenu.dark.admin.application.utils.logger
import com.kemenu.dark.admin.infrastructure.AdminSpringMongoRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AdminReactiveUserDetailsService(
        private val adminRepository: AdminSpringMongoRepository
) : ReactiveUserDetailsService {

    val logger = logger()

    override fun findByUsername(username: String?): Mono<UserDetails> {
        val email = username ?: throw BadCredentialsException("Invalid user or pass")
        return adminRepository.findAdminByEmail(email)
                .switchIfEmpty(Mono.error(BadCredentialsException("Invalid user or pass")))
                .doOnError {
                    logger.warn("Someone with {} username tried to log in", username)
                    throw it
                }
                .map { User(it.email, it.password, listOf(it)) }
    }
}