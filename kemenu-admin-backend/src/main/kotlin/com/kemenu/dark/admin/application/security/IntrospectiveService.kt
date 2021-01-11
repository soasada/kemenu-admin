package com.kemenu.dark.admin.application.security

import org.springframework.stereotype.Service

@Service
class IntrospectiveService(
        private val jwtService: JWTService
) {
    fun whoAmI(token: String): String = jwtService.decodeAccessToken(token).subject
}