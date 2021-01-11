package com.kemenu.dark.admin.domain

data class Menu(
        val id: String,
        val sections: List<MenuSection>,
        val imageUrl: String = "",
        val currency: Currency = Currency("EUR"), // TODO: This should be CurrencyUnit from moneta
        val name: String = "",
) {
    override fun equals(other: Any?) = other is Menu && EssentialMenuData(this) == EssentialMenuData(other)
    override fun hashCode() = EssentialMenuData(this).hashCode()

    private data class EssentialMenuData(val id: String) {
        constructor(menu: Menu) : this(menu.id)
    }

    data class Currency(val code: String)
}