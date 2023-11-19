package com.example.sc.data

import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val dao: ProductDao
): ProductRepository {
    override suspend fun insertProduct(product: Product) {
        this.dao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        this.dao.deleteProduct(product)
    }

    override suspend fun getProductById(id: Int): Product? {
        return this.dao.getProductById(id)
    }

    override fun getProducts(): Flow<List<Product>> {
        return this.dao.getProducts()
    }
}