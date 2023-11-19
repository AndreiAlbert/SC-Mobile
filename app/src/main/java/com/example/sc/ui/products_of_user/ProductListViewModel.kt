package com.example.sc.ui.products_of_user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sc.data.ProductRepository
import com.example.sc.utils.Routes
import com.example.sc.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
):ViewModel(){
    val products = repository.getProducts()
    private val _uiEvent = Channel<UiEvent>();
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: ProductListEvent) {
        when(event) {
            is ProductListEvent.OnProductClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_PRODUCT + "?productId=${event.product.id}"))
            }
            is ProductListEvent.OnAddProductClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_PRODUCT))
            }
            is ProductListEvent.OnDeleteProductClick -> {
                viewModelScope.launch {
                    repository.deleteProduct(event.product)
                }
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event);
        }
    }
}