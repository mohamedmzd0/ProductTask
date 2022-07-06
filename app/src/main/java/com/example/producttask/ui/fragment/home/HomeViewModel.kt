package com.example.producttask.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.producttask.data.Product
import com.example.producttask.repo.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {

    fun getProducts() = repository.getAllProducts()


    fun insertData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = ArrayList<Product>()
            for (i in 1..10) {
                data.add(
                    Product(
                        productID = i,
                        productTitle = getRandomString(7),
                        dateAddedToCart = null
                    )
                )
            }
            repository.insertProducts(data)
        }
    }

    private fun getRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    fun addToCard(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProductToCart(id)
        }
    }

}