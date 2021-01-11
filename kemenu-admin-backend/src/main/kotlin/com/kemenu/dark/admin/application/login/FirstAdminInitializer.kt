package com.kemenu.dark.admin.application.login

import com.kemenu.dark.admin.domain.Admin
import com.kemenu.dark.admin.infrastructure.AdminSpringMongoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class FirstAdminInitializer(
        private val adminRepository: AdminSpringMongoRepository,
        private val passwordEncoder: PasswordEncoder,
        @Value("\${app.admin.username}") val adminUsername: String,
        @Value("\${app.admin.password}") val adminPassword: String
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @EventListener(ApplicationReadyEvent::class)
    fun init() {
        adminRepository.findAdminByEmail(adminUsername)
                .doOnNext { logger.info("First admin already created") }
                .switchIfEmpty(
                        Mono.just(Admin(UUID.randomUUID().toString(), adminUsername, passwordEncoder.encode(adminPassword), Admin.Role.SUPER_ADMIN))
                                .doOnNext {
                                    adminRepository.save(it).subscribe { logger.info("Creating first admin: $adminUsername") }
                                }
                )
                .block()
    }
}