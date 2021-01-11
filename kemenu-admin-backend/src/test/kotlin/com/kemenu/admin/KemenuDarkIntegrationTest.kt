package com.kemenu.admin

import com.kemenu.admin.application.security.JWTService
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = ["test"])
class KemenuDarkIntegrationTest {

    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @Autowired
    protected lateinit var jwtService: JWTService

    protected fun accessToken() = accessToken("superAdmin@example.com")
    protected fun adminAccessToken() = adminAccessToken("admin@example.com")
    protected fun moderatorAccessToken() = moderatorAccessToken("moderator@example.com")

    protected fun accessToken(email: String) = "Bearer " + jwtService.accessToken(email, 1000 * 60, arrayOf("ROLE_SUPER_ADMIN"))
    protected fun adminAccessToken(email: String) = "Bearer " + jwtService.accessToken(email, 1000 * 60, arrayOf("ROLE_ADMIN"))
    protected fun moderatorAccessToken(email: String) = "Bearer " + jwtService.accessToken(email, 1000 * 60, arrayOf("ROLE_MODERATOR"))

    protected fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T
}