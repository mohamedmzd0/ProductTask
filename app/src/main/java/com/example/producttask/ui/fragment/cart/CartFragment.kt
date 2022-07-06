package com.example.producttask.ui.fragment.cart

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.producttask.R
import com.example.producttask.data.Product
import com.example.producttask.databinding.FragmentCartBinding
import com.example.producttask.utils.extentions.getThreeDaysAgoDate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var _context: Context
    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding
        get() = _binding!!
    private val cartViewModel by viewModels<CartViewModel>()

    private val cartAdapter by lazy {
        AdapterCart {
            deleteFromCart(it)
        }
    }

    private fun deleteFromCart(id: Int) {
        cartViewModel.removeFromCart(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)
        _context = requireContext()


        setupRecyclerView()
        setupObserver()


    }

    private fun setupObserver() {
        cartViewModel.getCartLiveData(getThreeDaysAgoDate())
            .observe(viewLifecycleOwner) { data ->
                when {
                    data.isNullOrEmpty() -> {
                        showEmpty()
                    }
                    else -> {
                        showData(data)
                    }
                }
            }
    }

    private fun showData(data: List<Product>) {
        cartAdapter.setAll(data)
        binding.apply {
            recyclerViewCart.isVisible = true
            tvEmpty.isVisible = false
        }
    }

    private fun showEmpty() {
        binding.apply {
            recyclerViewCart.isVisible = false
            tvEmpty.isVisible = true
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCart.apply {
            setHasFixedSize(true)
            adapter = cartAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}