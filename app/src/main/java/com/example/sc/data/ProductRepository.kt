package com.example.sc.data

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProduct(product: Product)

    suspend fun deleteProduct(product: Product)

    suspend fun getProductById(id: Int): Product?

    fun getProducts(): Flow<List<Product>>
}