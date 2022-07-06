package com.example.producttask.app

import android.content.Context
import com.example.producttask.db.ProductDao
import com.example.producttask.db.ProductDatabase
import com.example.producttask.repo.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideProductsRepository(productDao: ProductDao): ProductsRepository =
        ProductsRepository(productDao)

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDao =
        ProductDatabase.getDatabase(context).getProductDao()

}