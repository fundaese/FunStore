package com.example.funstore.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int?,
    val title: String?,
    val price: Double?,
    val salePrice: Int?,
    val description: String?,
    val category: String?,
    val imageOne: String?,
    val imageTwo: String?,
    val imageThree: String?,
    val rate: Double?,
    val count: Int?,
    val saleState: Boolean?
    ): Parcelable
