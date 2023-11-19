package com.example.sc.ui.products_of_user

import com.example.sc.data.Product

sealed class ProductListEvent {
    object OnAddProductClick: ProductListEvent()
    data class OnDeleteProductClick(val product: Product): ProductListEvent()
    data class OnProductClick(val product: Product): ProductListEvent()
}
