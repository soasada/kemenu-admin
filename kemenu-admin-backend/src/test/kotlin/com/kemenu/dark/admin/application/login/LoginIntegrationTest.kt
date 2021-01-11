package com.kemenu.dark.admin.application.login

import com.kemenu.dark.admin.KemenuDarkIntegrationTest
import com.kemenu.dark.admin.application.security.recaptcha.RecaptchaService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.mock.mockito.MockBean
import reactor.core.publisher.Mono

class LoginIntegrationTest : KemenuDarkIntegrationTest() {

    @Value("\${app.admin.username}")
    private lateinit var adminUsername: String

    @Value("\${app.admin.password}")
    private lateinit var adminPassword: String

    @MockBean
    private lateinit var recaptchaService: RecaptchaService

    @BeforeEach
    fun initAll() {
        Mockito.`when`(recaptchaService.valid("TOKEN")).thenReturn(Mono.just(true))
    }

    @Test
    fun `Given an existing admin when tries to login then get an access and refresh token`() {
        val responseHeaders = webTestClient
                .post().uri("/v1/login")
                .bodyValue(LoginRequest(adminUsername, adminPassword, "TOKEN"))
                .exchange()
                .expectStatus().isOk
                .expectHeader().exists("Authorization")
                .expectHeader().exists("JWT-Refresh-Token")
                .expectBody().returnResult().responseHeaders

        val accessToken = responseHeaders["Authorization"]?.get(0)
        val refreshToken = responseHeaders["JWT-Refresh-Token"]?.get(0)

        jwtService.decodeAccessToken(accessToken!!)
        jwtService.decodeRefreshToken(refreshToken!!)
    }

    @Test
    fun `Given an unknown admin when try to login then receives an UNAUTHORIZED error`() {
        webTestClient
                .post().uri("/v1/login")
                .bodyValue(LoginRequest("unknown@example.com", "unknownpassword", "TOKEN"))
                .exchange()
                .expectStatus().isUnauthorized
    }

    @Test
    fun `Given an admin when tries to login with a username that is not a valid email then receives BAD REQUEST error`() {
        webTestClient
                .post().uri("/v1/login")
                .bodyValue(LoginRequest("invalid@asd", "invalid", "TOKEN"))
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `Given an admin when tries to login with correct email but not sized password then receives BAD REQUEST error`() {
        webTestClient
                .post().uri("/v1/login")
                .bodyValue(LoginRequest("invalid@asd.com", "invalid", "TOKEN"))
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `Given an admin when tries to login with incorrect JSON request then receives BAD REQUEST error`() {
        val badRequest = object {
            val invalidUsername = "invalid@asd.com"
            val password = "invalid"
        }

        webTestClient
                .post().uri("/v1/login")
                .bodyValue(badRequest)
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `Given an admin when tries to login making a GET request then receives NOT FOUND error`() {
        webTestClient
                .get().uri("/v1/login")
                .exchange()
                .expectStatus().isNotFound
    }

    @Test
    fun `Given an admin when tries to login but the recaptcha is wrong then receives a BAD REQUEST`() {
        Mockito.`when`(recaptchaService.valid("BAD_TOKEN")).thenReturn(Mono.just(false))

        webTestClient
                .post().uri("/v1/login")
                .bodyValue(LoginRequest("admin@example.com", "adminPassword", "BAD_TOKEN"))
                .exchange()
                .expectStatus().isUnauthorized
    }

    @Test
    fun `Given an existing admin when tries to login then get an access and refresh token with roles`() {
        val responseHeaders = webTestClient
                .post().uri("/v1/login")
                .bodyValue(LoginRequest(adminUsername, adminPassword, "TOKEN"))
                .exchange()
                .expectStatus().isOk
                .expectHeader().exists("Authorization")
                .expectHeader().exists("JWT-Refresh-Token")
                .expectBody().returnResult().responseHeaders

        val accessToken = responseHeaders["Authorization"]?.get(0)
        val refreshToken = responseHeaders["JWT-Refresh-Token"]?.get(0)

        val accessTokenRoles = jwtService.getRoles(jwtService.decodeAccessToken(accessToken!!))
        val refreshTokenRoles = jwtService.getRoles(jwtService.decodeRefreshToken(refreshToken!!))

        assertFalse(accessTokenRoles.isEmpty())
        assertFalse(refreshTokenRoles.isEmpty())
        assertEquals(accessTokenRoles[0].authority, refreshTokenRoles[0].authority)
        assertEquals("ROLE_SUPER_ADMIN", accessTokenRoles[0].authority)
    }
}
