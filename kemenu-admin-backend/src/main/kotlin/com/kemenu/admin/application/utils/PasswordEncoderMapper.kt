package com.kemenu.admin.application.utils

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordEncoderMapper(
        private val passwordEncoder: PasswordEncoder
) {

    @EncodedMapping
    fun encode(value: String): String {
        return passwordEncoder.encode(value)
    }
}