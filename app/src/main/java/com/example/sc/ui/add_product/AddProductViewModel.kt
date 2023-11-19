package com.example.sc.ui.add_product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sc.data.Product
import com.example.sc.data.ProductRepository
import com.example.sc.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var product by mutableStateOf<Product?>(null)
        private set

    var name by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>();
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val productId = savedStateHandle.get<Int>("productId")!!
        if(productId != -1) {
            viewModelScope.launch {
                repository.getProductById(productId)?.let {
                    name = it.name
                    description = it.description?:""
                    price = it.price.toString()
                    this@AddProductViewModel.product = it
                }
            }
        }
    }

    fun onEvent(event: AddProductEvent){
        when(event) {
            is AddProductEvent.OnNameChange -> {
                name = event.name
            }
            is AddProductEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddProductEvent.OnPriceChange -> {
                price = event.price
            }
            is AddProductEvent.OnSaveProductClick -> {
                viewModelScope.launch {
                    if(name.isBlank()){
                        return@launch
                    }
                    val convertedPrice = price.toInt()
                    repository.insertProduct(
                        Product(
                            name = name,
                            description = description,
                            price = convertedPrice,
                            id = product?.id
                        )
                    )
                    senUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }
    private fun senUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}