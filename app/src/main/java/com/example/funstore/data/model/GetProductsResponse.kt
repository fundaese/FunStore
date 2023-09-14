package com.example.funstore.data.model

data class GetProductsResponse(
    val status: Int?,
    val message: String?,
    val products: List<Product>?
)
