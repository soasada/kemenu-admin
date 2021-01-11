package com.kemenu.dark.admin.application.admin

import com.kemenu.dark.admin.KemenuDarkIntegrationTest
import com.kemenu.dark.admin.domain.Admin
import com.kemenu.dark.admin.helper.AdminHelper
import com.kemenu.dark.admin.infrastructure.AdminSpringMongoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Mono
import java.util.UUID

class AdminIntegrationTest(
        @Autowired private val adminRepository: AdminSpringMongoRepository,
        @Autowired private val passwordEncoder: PasswordEncoder
) : KemenuDarkIntegrationTest() {

    @Test
    fun `Should return the admin list`() {
        val admin = AdminHelper.random()
        adminRepository.save(admin).block()
        webTestClient
                .get().uri("/v1/admins")
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk
                .expectBodyList<Admin>()
                .contains(admin)
    }

    @Test
    fun `Should create an Admin`() {
        val email = UUID.randomUUID().toString() + "@example.com"
        val password = "adminPassword"
        val role = Admin.Role.MODERATOR

        webTestClient
                .post().uri("/v1/admins")
                .body(Mono.just(CreateAdminRequest(email, password, role)), CreateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk
    }

    @Test
    fun `Should return CONFLICT if an Admin is already created`() {
        val email = UUID.randomUUID().toString() + "@example.com"
        val password = "adminPassword"
        val role = Admin.Role.MODERATOR

        webTestClient
                .post().uri("/v1/admins")
                .body(Mono.just(CreateAdminRequest(email, password, role)), CreateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk

        webTestClient
                .post().uri("/v1/admins")
                .body(Mono.just(CreateAdminRequest(email, password, role)), CreateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
    }

    @Test
    fun `Should return BAD REQUEST if invalid email is provided`() {
        val email = "invalid@example"
        val password = "asd"
        val role = Admin.Role.MODERATOR

        webTestClient
                .post().uri("/v1/admins")
                .body(Mono.just(CreateAdminRequest(email, password, role)), CreateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `Should return a 401 if admin is not SUPER_ADMIN`() {
        webTestClient
                .get().uri("/v1/admins")
                .header(HttpHeaders.AUTHORIZATION, adminAccessToken())
                .exchange()
                .expectStatus().isUnauthorized

        webTestClient
                .get().uri("/v1/admins")
                .header(HttpHeaders.AUTHORIZATION, moderatorAccessToken())
                .exchange()
                .expectStatus().isUnauthorized
    }

    @Test
    fun `Should not update password if password is not changed`() {
        val email = UUID.randomUUID().toString() + "@example.com"
        val password = "adminPassword"
        val role = Admin.Role.MODERATOR

        webTestClient
                .post().uri("/v1/admins")
                .body(Mono.just(CreateAdminRequest(email, password, role)), CreateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk

        val createdAdmin = adminRepository.findAdminByEmail(email).block() ?: throw Exception("")

        webTestClient
                .put().uri("/v1/admins")
                .body(Mono.just(UpdateAdminRequest(createdAdmin.id, createdAdmin.email, createdAdmin.password, Admin.Role.ADMIN)), UpdateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk

        val updatedAdmin = adminRepository.findAdminByEmail(email).block() ?: throw Exception("")

        assertEquals(createdAdmin.id, updatedAdmin.id)
        assertEquals(Admin.Role.ADMIN, updatedAdmin.role)
        assertEquals(createdAdmin.password, updatedAdmin.password)
        assertEquals(createdAdmin.createdAt, updatedAdmin.createdAt)
        assertNotEquals(createdAdmin.updatedAt, updatedAdmin.updatedAt)
    }

    @Test
    fun `Should update password if password is changed`() {
        val email = UUID.randomUUID().toString() + "@example.com"
        val password = "adminPassword"
        val role = Admin.Role.MODERATOR

        webTestClient
                .post().uri("/v1/admins")
                .body(Mono.just(CreateAdminRequest(email, password, role)), CreateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk

        val createdAdmin = adminRepository.findAdminByEmail(email).block() ?: throw Exception("")

        webTestClient
                .put().uri("/v1/admins")
                .body(Mono.just(UpdateAdminRequest(createdAdmin.id, createdAdmin.email, "newPassword", Admin.Role.ADMIN)), UpdateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk

        val updatedAdmin = adminRepository.findAdminByEmail(email).block() ?: throw Exception("")

        assertEquals(createdAdmin.id, updatedAdmin.id)
        assertEquals(Admin.Role.ADMIN, updatedAdmin.role)
        assertNotEquals(createdAdmin.password, updatedAdmin.password)
        assertEquals(createdAdmin.createdAt, updatedAdmin.createdAt)
        assertNotEquals(createdAdmin.updatedAt, updatedAdmin.updatedAt)
        assertTrue(passwordEncoder.matches("newPassword", updatedAdmin.password))
    }

    @Test
    fun `Should return a 404 when an admin tries to update an unknown admin`() {
        webTestClient
                .put().uri("/v1/admins")
                .body(Mono.just(UpdateAdminRequest("unknownId", "another@example.com", "newPassword", Admin.Role.SUPER_ADMIN)), UpdateAdminRequest::class.java)
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isNotFound
    }

    @Test
    fun `Only SUPER_ADMINs can delete admins`() {
        val admin = AdminHelper.random()
        adminRepository.save(admin).block()

        webTestClient
                .delete().uri("/v1/admins/${admin.id}")
                .header(HttpHeaders.AUTHORIZATION, moderatorAccessToken())
                .exchange()
                .expectStatus().isUnauthorized

        webTestClient
                .delete().uri("/v1/admins/${admin.id}")
                .header(HttpHeaders.AUTHORIZATION, adminAccessToken())
                .exchange()
                .expectStatus().isUnauthorized

        webTestClient
                .delete().uri("/v1/admins/${admin.id}")
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk

        val emptyCustomer = adminRepository.findById(admin.id).block()
        assertNull(emptyCustomer)
    }
}