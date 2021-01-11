package com.kemenu.admin.application.upload_image

import com.kemenu.admin.KemenuDarkIntegrationTest
import com.kemenu.admin.application.cloudinary.CloudinaryService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Mono
import java.util.UUID

internal class UploadImageIntegrationTest : KemenuDarkIntegrationTest() {

    @MockBean
    private lateinit var cloudinaryService: CloudinaryService

    @BeforeEach
    fun initAll() {
        `when`(cloudinaryService.upload(any())).thenReturn(Mono.just(UUID.randomUUID().toString()))
    }

    @Test
    fun `Given an admin when tries to upload an image then the image is uploaded`() {
        val response = webTestClient
                .post().uri("/v1/upload/image")
                .body(BodyInserters.fromMultipartData(generatePhoto().build()))
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk
                .expectBody(UploadImageResponse::class.java).returnResult().responseBody!!

        assertFalse(response.url.isEmpty())
    }

    private fun generatePhoto(): MultipartBodyBuilder {
        val builder = MultipartBodyBuilder()
        builder
                .part("file", ClassPathResource("kemenu.png"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
        return builder
    }
}