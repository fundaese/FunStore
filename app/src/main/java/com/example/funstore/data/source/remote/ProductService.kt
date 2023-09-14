package com.example.funstore.data.source.remote

import com.example.funstore.common.Constants.Endpoint.GET_PRODUCTS
import com.example.funstore.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.example.funstore.common.Constants.Endpoint.GET_SALE_PRODUCTS
import com.example.funstore.data.model.GetProductDetailResponse
import com.example.funstore.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductService {
    @GET(GET_PRODUCTS)
    fun getProducts(
        @Header("store") storeValue: String
    ): Call<GetProductsResponse>

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): Call<GetProductDetailResponse>

    @GET(GET_SALE_PRODUCTS)
    fun getSaleProducts(
        @Header("store") storeValue: String
    ): Call<GetProductsResponse>
}