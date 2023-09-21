package com.example.funstore.data.repository

import com.example.funstore.common.Resource
import com.example.funstore.data.model.ProductUI
import com.example.funstore.data.source.local.ProductDao
import com.example.funstore.data.source.remote.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
        private val productService: ProductService,
        private val productDao: ProductDao,
) {

    suspend fun getProducts(): Resource<List<ProductUI>> {
        return try {
            Resource.Success(productService.getProducts().products?.map { it.mapToProductUI() }.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSaleProducts(): Resource<List<ProductUI>> {
        return try {
            Resource.Success(productService.getSaleProducts().products?.map { it.mapToProductUI() }.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getProductsByCategory(category: String): Resource<List<ProductUI>>  {
        return try {
            Resource.Success(productService.getProductsByCategory(category).products?.map { it.mapToProductUI() }.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getProductsDetail(id: Int): Resource<ProductUI> {
        return try {
            productService.getProductDetail(id).product?.let {
                Resource.Success(it.mapToProductUI())
            } ?: kotlin.run {
                Resource.Error(Exception("Product not found"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSearchProduct(query: String): Resource<List<ProductUI>> {
        return try {
            val response = productService.getSearchProduct(query)
            Resource.Success(response.products?.map { it.mapToProductUI() }.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun addProductToCart(product: ProductUI) {
        productDao.addProduct(product.mapToProductEntity())
    }

    suspend fun deleteProductFromCart(product: ProductUI) {
        productDao.deleteProduct(product.mapToProductEntity())
    }

    suspend fun getCartProducts(): Resource<List<ProductUI>> {
        return try {
            Resource.Success(productDao.getProducts().map {
                it.mapToProductUI()
            })
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun addProductToFav(product: ProductUI) {
        productDao.addProduct(product.mapToProductEntity())
    }

    suspend fun deleteProductFromFav(product: ProductUI) {
        productDao.deleteProduct(product.mapToProductEntity())
    }

    suspend fun getFavProducts(): Resource<List<ProductUI>> {
        return try {
            Resource.Success(productDao.getProducts().map {
                it.mapToProductUI()
            })
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}