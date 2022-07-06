package com.example.producttask.repo

import androidx.lifecycle.LiveData
import com.example.producttask.data.Product
import com.example.producttask.db.ProductDao
import java.util.*

class ProductsRepository(private val productDao: ProductDao) {

    fun getAllProducts(): LiveData<List<Product>> = productDao.getAllProducts()

    fun getCart(dateStart: Date, dateEnd: Date): LiveData<List<Product>> =
        productDao.getCart(dateStart, dateEnd)

    suspend fun insertProducts(products: List<Product>) = productDao.insertProducts(products)

    suspend fun deleteProductFromCart(id: Int) = productDao.removeFromCart(id)

    suspend fun addProductToCart(id: Int) = productDao.addToCart(id, Date())
}