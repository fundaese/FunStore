package com.example.funstore.di

import com.example.funstore.data.repository.ProductRepository
import com.example.funstore.data.source.local.ProductDao
import com.example.funstore.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(productService: ProductService, productDao: ProductDao) : ProductRepository =
        ProductRepository(productService, productDao)

}