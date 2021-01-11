package com.kemenu.dark.admin.application.blog

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank

data class BlogPostRequest(
        @field:NotBlank
        val title: String,
        @field:NotBlank
        val content: String,
        @field:NotBlank
        @field:URL
        val imageUrl: String,
        @field:NotBlank
        val locale: String
)