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

    fun getProducts() = repository.allProductsFlow


    fun insertData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRandomProducts()
        }
    }

    fun addToCard(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProductToCart(id)
        }
    }

}