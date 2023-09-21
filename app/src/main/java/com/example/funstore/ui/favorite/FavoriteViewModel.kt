package com.example.funstore.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.funstore.common.Resource
import com.example.funstore.data.model.Product
import com.example.funstore.data.model.ProductUI
import com.example.funstore.data.repository.ProductRepository
import com.example.funstore.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val productRepository: ProductRepository, application: Application
) : BaseViewModel(application) {

    private var _favState = MutableLiveData<FavState>()
    val favState: LiveData<FavState>
        get() = _favState

    fun getFavProducts() {
        launch {
            _favState.value = FavState.Loading
            when (val result = productRepository.getCartProducts()) {
                is Resource.Success -> {
                    _favState.value = FavState.Data(result.data)
                }

                is Resource.Error -> {
                    _favState.value = FavState.Error(result.throwable)
                }
            }
        }
    }

    fun deleteProduct(product: ProductUI) {
        launch {
            productRepository.deleteProductFromCart(product)
        }
    }
}

sealed interface FavState {
    object Loading : FavState
    data class Data(val products: List<ProductUI>) : FavState
    data class Error(val throwable: Throwable) : FavState
}