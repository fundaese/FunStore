package com.example.funstore.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.funstore.data.model.GetProductDetailResponse
import com.example.funstore.data.model.GetProductsResponse
import com.example.funstore.data.model.Product
import com.example.funstore.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
        private val productService: ProductService
    ) {
    val productsLiveData = MutableLiveData<List<Product>?>()
    val salesProductsLiveData = MutableLiveData<List<Product>?>()
    val errorMessageLiveData = MutableLiveData<String>()
    val productsDetailLiveData = MutableLiveData<Product?>()
    val loadingLiveData = MutableLiveData<Boolean>()

    suspend fun getProducts(): Response<GetProductsResponse> {
        loadingLiveData.value = true
        return try {
            val response = productService.getProducts("funstore")

            if (response.isSuccessful) {
                val result = response.body()?.products

                if (result.isNullOrEmpty().not()) {
                    productsLiveData.value = result
                } else {
                    productsLiveData.value = null
                }
            } else {
                errorMessageLiveData.value = "Response error: ${response.code()}"
            }

            loadingLiveData.value = false
            response
        } catch (t: Throwable) {
            errorMessageLiveData.value = t.message.orEmpty()
            loadingLiveData.value = false
            Log.e("GetProducts", t.message.orEmpty())
            Response.error(500, ResponseBody.create(null, ""))
        }
    }

    suspend fun getSaleProducts(): Response<GetProductsResponse> {
        loadingLiveData.value = true
        return try {
            val response = productService.getSaleProducts("funstore")

            if (response.isSuccessful) {
                val result = response.body()?.products

                if (result.isNullOrEmpty().not()) {
                    salesProductsLiveData.value = result
                } else {
                    salesProductsLiveData.value = null
                }
            } else {
                errorMessageLiveData.value = "Response error: ${response.code()}"
            }

            loadingLiveData.value = false
            response
        } catch (t: Throwable) {
            errorMessageLiveData.value = t.message.orEmpty()
            loadingLiveData.value = false
            Log.e("GetSaleProducts", t.message.orEmpty())
            Response.error(500, ResponseBody.create(null, ""))
        }
    }

    suspend fun getProductsByCategory(category: String): Response<GetProductsResponse> {
        loadingLiveData.value = true
        return try {
            val response = productService.getProductsByCategory("funstore", category)

            if (response.isSuccessful) {
                val result = response.body()?.products

                if (result.isNullOrEmpty().not()) {
                    productsLiveData.value = result
                } else {
                    productsLiveData.value = null
                }
            } else {
                errorMessageLiveData.value = "Response error: ${response.code()}"
            }

            loadingLiveData.value = false
            response
        } catch (t: Throwable) {
            errorMessageLiveData.value = t.message.orEmpty()
            loadingLiveData.value = false
            Log.e("GetProductsByCategory", t.message.orEmpty())
            Response.error(500, ResponseBody.create(null, ""))
        }
    }

    suspend fun getProductsDetail(id: Int): Response<GetProductDetailResponse> {
        loadingLiveData.value = true

        return try {
            val response = productService.getProductDetail("funstore",id)

            if (response.isSuccessful) {
                val result = response.body()?.product

                if (result != null) {
                    productsDetailLiveData.value = result
                } else {
                    productsDetailLiveData.value = null
                }
            } else {
                errorMessageLiveData.value = "Response error: ${response.code()}"
            }

            loadingLiveData.value = false
            response
        } catch (t: Throwable) {
            errorMessageLiveData.value = t.message.orEmpty()
            loadingLiveData.value = false
            Log.e("GetProductsDetail", t.message.orEmpty())
            Response.error(500, ResponseBody.create(null, ""))
        }
    }

}