package com.example.funstore.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.funstore.common.Resource
import com.example.funstore.data.model.Product
import com.example.funstore.data.model.ProductUI
import com.example.funstore.data.repository.ProductRepository
import com.example.funstore.ui.home.HomeState
import com.example.funstore.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository, application: Application
): BaseViewModel(application) {

    private var _productDetailState = MutableLiveData<ProductDetailState>()
    val productDetailState: LiveData<ProductDetailState>
        get() = _productDetailState

    fun getProductsDetail(id: Int) {
        launch {
            _productDetailState.value = ProductDetailState.Loading
            val result = productRepository.getProductsDetail(id)

            when (result) {
                is Resource.Success -> {
                    _productDetailState.value = ProductDetailState.Data(result.data)
                }

                is Resource.Error -> {
                    _productDetailState.value = ProductDetailState.Error(result.throwable)
                }
            }
        }
    }

    fun addProductToCart(product: ProductUI) {
        launch {
            productRepository.addProductToCart(product)
        }
    }
}

sealed interface ProductDetailState {
    object Loading: ProductDetailState
    data class Data(val productResponse: ProductUI): ProductDetailState
    data class Error(val throwable: Throwable): ProductDetailState
}