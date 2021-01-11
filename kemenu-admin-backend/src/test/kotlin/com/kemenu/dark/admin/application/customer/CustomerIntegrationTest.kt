package com.kemenu.dark.admin.application.customer

import com.kemenu.dark.admin.KemenuDarkIntegrationTest
import com.kemenu.dark.admin.domain.Customer
import com.kemenu.dark.admin.helper.CustomerHelper
import com.kemenu.dark.admin.infrastructure.CustomerSpringMongoRepository
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.expectBodyList

class CustomerIntegrationTest(
        @Autowired private val customerRepository: CustomerSpringMongoRepository
) : KemenuDarkIntegrationTest() {

    @Test
    fun `Given an admin when tries to fetch data from customers API without AUTHORIZATION header then receives an UNAUTHORIZED error`() {
        webTestClient
                .get().uri("/v1/customers")
                .exchange()
                .expectStatus().isUnauthorized
    }

    @Test
    fun `Given an admin when tries to fetch data from customers API with AUTHORIZATION header but without starting with Bearer then receives an UNAUTHORIZED error`() {
        webTestClient
                .get().uri("/v1/customers")
                .header(HttpHeaders.AUTHORIZATION, accessToken().replace("Bearer ", ""))
                .exchange()
                .expectStatus().isUnauthorized
    }

    @Test
    fun `Given an admin when tries to fetch data from customers API with AUTHORIZATION header but with a not compliant Bearer token then receives an UNAUTHORIZED error`() {
        webTestClient
                .get().uri("/v1/customers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .exchange()
                .expectStatus().isUnauthorized
    }

    @Test
    fun `Given an admin when tries to fetch data from customers API with AUTHORIZATION then receives the data`() {
        val customer = CustomerHelper.random()
        customerRepository.save(customer).block()
        webTestClient
                .get().uri("/v1/customers")
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk
                .expectBodyList<Customer>()
                .contains(customer)
    }

    @Test
    fun `Only SUPER_ADMINs can delete customers`() {
        val customer = CustomerHelper.random()
        customerRepository.save(customer).block()

        webTestClient
                .delete().uri("/v1/customers/${customer.id}")
                .header(HttpHeaders.AUTHORIZATION, moderatorAccessToken())
                .exchange()
                .expectStatus().isUnauthorized

        webTestClient
                .delete().uri("/v1/customers/${customer.id}")
                .header(HttpHeaders.AUTHORIZATION, adminAccessToken())
                .exchange()
                .expectStatus().isUnauthorized

        webTestClient
                .delete().uri("/v1/customers/${customer.id}")
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isOk

        val emptyCustomer = customerRepository.findById(customer.id).block()
        assertNull(emptyCustomer)
    }

    @Test
    fun `Should return a 404 when an admin tries to remove a unknown customer`() {
        webTestClient
                .delete().uri("/v1/customers/unknownId")
                .header(HttpHeaders.AUTHORIZATION, accessToken())
                .exchange()
                .expectStatus().isNotFound
    }
}