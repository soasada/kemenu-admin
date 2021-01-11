package com.kemenu.dark.admin.domain

import java.math.BigDecimal

data class Dish(
        val name: String,
        val description: String?,
        val price: BigDecimal,
        val allergens: List<Allergen>,
        val imageUrl: String?,
        val available: Boolean?
)

