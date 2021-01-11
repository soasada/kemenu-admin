package com.kemenu.dark.admin.domain

import org.springframework.data.annotation.Id

data class Business(
        @Id val id: String,
        val name: String,
        val menus: List<Menu>,
        val imageUrl: String?,
        val phone: String?,
        val info: String?,
        val color: String?,
) {
    override fun equals(other: Any?) = other is Business && EssentialBusinessData(this) == EssentialBusinessData(other)
    override fun hashCode() = EssentialBusinessData(this).hashCode()

    private data class EssentialBusinessData(val id: String) {
        constructor(business: Business) : this(business.id)
    }
}