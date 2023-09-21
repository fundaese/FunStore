package com.example.funstore.data.model

data class ProductUI(
    val id: Int,
    val title: String,
    val price: Double,
    val salePrice: Double,
    val description: String,
    val category: String,
    val imageOne: String,
    val imageTwo: String,
    val imageThree: String,
    val rate: Double,
    val count: Int,
    val saleState: Boolean
) {
    fun mapToProductEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            title = title,
            price = price,
            salePrice = salePrice,
            description = description,
            category = category,
            imageOne = imageOne,
            imageTwo = imageTwo,
            imageThree = imageThree,
            rate = rate,
            count = count,
            saleState = saleState
        )
    }
}
