package com.kemenu.admin.application.admin

import com.kemenu.admin.application.HttpExceptionFactory.conflict
import com.kemenu.admin.application.HttpExceptionFactory.notFound
import com.kemenu.admin.application.utils.logger
import com.kemenu.admin.infrastructure.AdminSpringMongoRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/v1")
class AdminController(
        private val adminRepository: AdminSpringMongoRepository,
        private val adminMapper: AdminMapper,
        private val passwordEncoder: PasswordEncoder
) {

    val logger = logger()

    @GetMapping("/admins")
    fun findAll() = adminRepository.findAll()
            .doOnError { logger.error("Error listing admins", it) }

    @GetMapping("/admins/{id}")
    fun find(@PathVariable id: String) = adminRepository
            .findById(id)
            .doOnError { logger.error("Error finding admins", it) }

    @PostMapping("/admins")
    fun create(@RequestBody @Valid createAdminRequest: CreateAdminRequest) = adminRepository
            .findAdminByEmail(createAdminRequest.email)
            .doOnNext {
                throw conflict()
            }
            .switchIfEmpty(
                    Mono.defer {
                        adminRepository.save(adminMapper.toEntity(createAdminRequest))
                                .doOnError { logger.error("Error while creating admin", it) }
                    }
            )

    @PutMapping("/admins")
    fun update(@RequestBody @Valid updateAdminRequest: UpdateAdminRequest) = adminRepository
            .findById(updateAdminRequest.id)
            .switchIfEmpty(Mono.error(notFound()))
            .flatMap {
                var newPassword = updateAdminRequest.password
                if (it.password != newPassword) {
                    newPassword = passwordEncoder.encode(newPassword)
                }
                val entity = adminMapper.toEntity(
                        newPassword,
                        it.createdAt,
                        updateAdminRequest
                )
                adminRepository.save(entity)
            }
            .doOnError { logger.error("Error while updating admin", it) }

    @DeleteMapping("/admins/{id}")
    fun delete(@PathVariable id: String) = adminRepository
            .findById(id)
            .switchIfEmpty(Mono.error(notFound()))
            .flatMap { adminRepository.deleteById(id) }
            .doOnError { logger.error("Error while deleting admin", it) }
}