package com.example.funstore.data.source.remote

import com.example.funstore.common.Constants.Endpoint.ADD_CART_PRODUCTS
import com.example.funstore.common.Constants.Endpoint.CLEAR_CART_PRODUCTS
import com.example.funstore.common.Constants.Endpoint.DELETE_CART_PRODUCTS
import com.example.funstore.common.Constants.Endpoint.GET_CART_PRODUCTS
import com.example.funstore.common.Constants.Endpoint.GET_PRODUCTS
import com.example.funstore.common.Constants.Endpoint.GET_PRODUCTS_BY_CATEGORY
import com.example.funstore.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.example.funstore.common.Constants.Endpoint.GET_SALE_PRODUCTS
import com.example.funstore.common.Constants.Endpoint.GET_SEARCH_PRODUCT
import com.example.funstore.data.model.AddToCartRequest
import com.example.funstore.data.model.CRUDResponse
import com.example.funstore.data.model.ClearCartRequest
import com.example.funstore.data.model.DeleteFromCartRequest
import com.example.funstore.data.model.GetProductDetailResponse
import com.example.funstore.data.model.GetProductsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {
    @GET(GET_PRODUCTS)
    suspend fun getProducts(): GetProductsResponse

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(@Query("id") id: Int): GetProductDetailResponse

    @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(): GetProductsResponse

    @GET(GET_PRODUCTS_BY_CATEGORY)
    suspend fun getProductsByCategory(@Query("category") categoryValue: String): GetProductsResponse

    @GET(GET_SEARCH_PRODUCT)
    suspend fun getSearchProduct(@Query("query") queryValue: String): GetProductsResponse

    @GET(GET_CART_PRODUCTS)
    suspend fun getCartProducts(@Query("userId") userId: String): GetProductsResponse

    @POST(ADD_CART_PRODUCTS)
    suspend fun addProductToCart(@Body request: AddToCartRequest): CRUDResponse

    @POST(DELETE_CART_PRODUCTS)
    suspend fun deleteProductFromCart(@Body request: DeleteFromCartRequest): CRUDResponse

    @POST(CLEAR_CART_PRODUCTS)
    suspend fun clearProductFromCart(@Body request: ClearCartRequest): CRUDResponse
}