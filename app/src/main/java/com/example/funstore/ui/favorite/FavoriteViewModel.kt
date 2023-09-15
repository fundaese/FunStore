package com.example.funstore.ui.favorite

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.funstore.data.model.Product
import com.example.funstore.data.repository.ProductRepository
import com.example.funstore.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val productRepository: ProductRepository, application: Application
): BaseViewModel(application) {

    var _favoritesBasket = MutableLiveData<List<Product>>()

    var _isLoading = MutableLiveData<Boolean>()

    init {
        getproductsFavorites()
    }

    private fun getproductsFavorites() {

    }

    fun deleteFavoritesFromBasket(Id: Int) {
        getproductsFavorites()
    }

}