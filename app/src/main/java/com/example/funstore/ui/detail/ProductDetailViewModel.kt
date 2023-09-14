package com.example.funstore.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.funstore.data.model.Product
import com.example.funstore.data.repository.ProductRepository
import com.example.funstore.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository, application: Application
): BaseViewModel(application) {

    private var _productsDetailLiveData = MutableLiveData<Product?>()
    val productsDetailLiveData: LiveData<Product?>
        get() = _productsDetailLiveData

    private var _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    init {
        _productsDetailLiveData = productRepository.productsDetailLiveData
        _errorMessageLiveData = productRepository.errorMessageLiveData
        _loadingLiveData = productRepository.loadingLiveData
    }

    fun getProductsDetail(id: Int) {
        launch {
            productRepository.getProductsDetail(id)
        }
    }
}