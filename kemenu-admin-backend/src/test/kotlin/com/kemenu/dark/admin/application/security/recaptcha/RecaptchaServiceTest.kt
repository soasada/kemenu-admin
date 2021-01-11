package com.kemenu.dark.admin.application.security.recaptcha

import com.kemenu.dark.admin.application.utils.JsonUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

@SpringBootTest
@ActiveProfiles(profiles = ["dev"])
internal class RecaptchaServiceTest(
        @Autowired private val jsonUtils: JsonUtils
) {

    @MockBean
    private lateinit var exchangeFunction: ExchangeFunction

    private lateinit var recaptchaService: RecaptchaService

    @BeforeEach
    fun initEach() {
        val webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build()
        recaptchaService = RecaptchaService(webClient, "RECAPTCHATOKEN", jsonUtils)
    }

    @Test
    fun `Should return that is valid if the response says that`() {
        `when`(exchangeFunction.exchange(any(ClientRequest::class.java)))
                .thenReturn(RecaptchaResponseHelper.ok())

        StepVerifier
                .create(recaptchaService.valid("TOKEN"))
                .expectNext(true)
                .verifyComplete()
    }

    @Test
    fun `Should return that is not valid if the response cannot be deserialized`() {
        `when`(exchangeFunction.exchange(any(ClientRequest::class.java)))
                .thenReturn(RecaptchaResponseHelper.create("""{"mal": "formed"}"""))

        StepVerifier
                .create(recaptchaService.valid("TOKEN"))
                .expectNext(false)
                .verifyComplete()
    }
}