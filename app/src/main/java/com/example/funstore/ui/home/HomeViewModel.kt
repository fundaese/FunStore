package com.example.funstore.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.funstore.data.model.Product
import com.example.funstore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private var _productLiveData = MutableLiveData<List<Product>?>()
    val productLiveData: LiveData<List<Product>?>
        get() = _productLiveData

    private var _salesProductLiveData = MutableLiveData<List<Product>?>()
    val salesProductLiveData: LiveData<List<Product>?>
        get() = _salesProductLiveData


    private var _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    init {
        _productLiveData = productRepository.productsLiveData
        _salesProductLiveData = productRepository.salesProductsLiveData
        _errorMessageLiveData = productRepository.errorMessageLiveData
        _loadingLiveData = productRepository.loadingLiveData
    }

    fun getProducts() {
        productRepository.getProducts()
    }

    fun getSaleProducts() {
        productRepository.getSaleProducts()
    }
}