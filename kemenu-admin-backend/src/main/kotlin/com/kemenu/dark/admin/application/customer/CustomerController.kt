package com.kemenu.dark.admin.application.customer

import com.kemenu.dark.admin.application.HttpExceptionFactory.notFound
import com.kemenu.dark.admin.application.utils.logger
import com.kemenu.dark.admin.infrastructure.CustomerSpringMongoRepository
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1")
class CustomerController(
        private val customerRepository: CustomerSpringMongoRepository
) {

    val logger = logger()

    @GetMapping("/customers", produces = [TEXT_EVENT_STREAM_VALUE])
    fun findAll() = customerRepository.findAll()
            .doOnError { logger.error("Error listing customers", it) }

    @GetMapping("/customers/{id}")
    fun find(@PathVariable id: String) = customerRepository.findById(id)
            .switchIfEmpty(Mono.error(notFound()))

    @DeleteMapping("/customers/{id}")
    fun delete(@PathVariable id: String) = customerRepository.findById(id)
            .switchIfEmpty(Mono.error(notFound()))
            .map { customerRepository.deleteById(id).subscribe() }
}