package com.example.funstore.di

import android.content.Context
import androidx.room.Room
import com.example.funstore.data.source.local.ProductRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDBModule {

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProductRoomDB::class.java, "product_room_db").build()

    @Provides
    @Singleton
    fun provideDao(roomDB: ProductRoomDB) = roomDB.productsDao()

}