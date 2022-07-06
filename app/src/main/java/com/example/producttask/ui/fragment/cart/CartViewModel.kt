package com.example.producttask.ui.fragment.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.producttask.repo.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {


    fun getCartLiveData(startDate: Date) = repository.getCart(startDate, Date())


    fun removeFromCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProductFromCart(id)
        }
    }
}