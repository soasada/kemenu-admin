package com.kemenu.dark.admin.domain

data class Allergen(
        val id: String,
        val name: String
) {
    override fun equals(other: Any?) = other is Allergen && EssentialAllergenData(this) == EssentialAllergenData(other)
    override fun hashCode() = EssentialAllergenData(this).hashCode()

    private data class EssentialAllergenData(val id: String) {
        constructor(allergen: Allergen) : this(allergen.id)
    }
}