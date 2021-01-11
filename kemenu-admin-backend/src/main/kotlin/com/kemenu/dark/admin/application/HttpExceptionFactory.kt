package com.kemenu.dark.admin.application

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

object HttpExceptionFactory {

    fun notFound(): ResponseStatusException = ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")

    fun badRequest(): ResponseStatusException = ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request")

    fun unauthorized(): ResponseStatusException = ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")

    fun conflict(): ResponseStatusException = ResponseStatusException(HttpStatus.CONFLICT, "Conflict")

    fun server(): ResponseStatusException = ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error")
}