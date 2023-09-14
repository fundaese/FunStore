package com.example.funstore.ui.search

import android.app.Application
import com.example.funstore.data.repository.ProductRepository
import com.example.funstore.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository, application: Application
): BaseViewModel(application) {
}