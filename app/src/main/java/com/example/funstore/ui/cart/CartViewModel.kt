package com.example.funstore.ui.cart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.funstore.common.Resource
import com.example.funstore.data.model.ClearCartRequest
import com.example.funstore.data.model.DeleteFromCartRequest
import com.example.funstore.data.model.ProductUI
import com.example.funstore.data.repository.ProductRepository
import com.example.funstore.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository, application: Application
) : BaseViewModel(application) {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState

    fun getCartProducts(userId: String) {
        launch {
            _cartState.value = CartState.Loading
            when (val result = productRepository.getCartProduct(userId)) {
                is Resource.Success -> {
                    _cartState.value = CartState.Data(result.data)
                }

                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                }
            }
        }
    }

    fun deleteProduct(request: DeleteFromCartRequest) {
        launch {
            productRepository.deleteProductFromCart(request)
        }
    }

    fun clearProduct(request: ClearCartRequest) {
        launch {
            productRepository.clearProductFromCart(request)
        }
    }
}

sealed interface CartState {
    object Loading : CartState
    data class Data(val products: List<ProductUI>) : CartState
    data class Error(val throwable: Throwable) : CartState
}
