package com.example.producttask.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.producttask.data.Product
import com.example.producttask.db.ProductDao
import com.example.producttask.utils.extentions.getThreeDaysAgoDate
import java.util.*

class ProductsRepository(private val productDao: ProductDao) {


    val allProductsFlow = Pager(
        PagingConfig(pageSize = 15),
        pagingSourceFactory = { productDao.getAllProducts() }
    ).flow

    val cartFlow = Pager(
        PagingConfig(pageSize = 15),
        pagingSourceFactory = { productDao.getCart(getThreeDaysAgoDate(),Date()) }
    ).flow



    // handle business login in repo
    suspend fun insertRandomProducts() {
        val data = ArrayList<Product>()
        for (i in 1..1000) {
            data.add(
                Product(
                    productID = i,
                    productTitle = getRandomString(7),
                    dateAddedToCart = null
                )
            )
        }
        productDao.insertProducts(data)
    }

    private fun getRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }


    suspend fun deleteProductFromCart(id: Int) = productDao.removeFromCart(id)

    suspend fun addProductToCart(id: Int) = productDao.addToCart(id, Date())
}