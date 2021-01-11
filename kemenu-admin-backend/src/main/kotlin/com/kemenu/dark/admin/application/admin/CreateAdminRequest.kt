package com.kemenu.dark.admin.application.admin

import com.kemenu.dark.admin.domain.Admin
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateAdminRequest(
        @field:Email(regexp = ".+@.+\\..+")
        @field:NotBlank
        val email: String,
        @field:NotBlank
        @field:Size(min = 8, max = 255)
        val password: String,
        @field:NotNull
        val role: Admin.Role
)