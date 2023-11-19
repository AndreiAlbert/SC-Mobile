package com.example.sc.ui.add_product


sealed class AddProductEvent {
    data class OnNameChange(val name: String): AddProductEvent()
    data class OnDescriptionChange(val description: String): AddProductEvent()
    data class OnPriceChange(val price: String): AddProductEvent()
    object OnSaveProductClick: AddProductEvent()
}
