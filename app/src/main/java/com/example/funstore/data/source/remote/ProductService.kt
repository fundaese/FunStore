package com.example.funstore.data.source.remote

import com.example.funstore.common.Constants.Endpoint.GET_PRODUCTS
import com.example.funstore.common.Constants.Endpoint.GET_PRODUCTS_BY_CATEGORY
import com.example.funstore.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.example.funstore.common.Constants.Endpoint.GET_SALE_PRODUCTS
import com.example.funstore.data.model.GetProductDetailResponse
import com.example.funstore.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductService {
    @GET(GET_PRODUCTS)
    suspend fun getProducts(
        @Header("store") storeValue: String
    ): Response<GetProductsResponse>

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Header("store") storeValue: String,
        @Query("id") id: Int
    ): Response<GetProductDetailResponse>

    @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(
        @Header("store") storeValue: String
    ): Response<GetProductsResponse>

    @GET(GET_PRODUCTS_BY_CATEGORY)
    suspend fun getProductsByCategory(
        @Header("store") storeValue: String,
        @Query("category") categoryValue: String
    ): Response<GetProductsResponse>
}