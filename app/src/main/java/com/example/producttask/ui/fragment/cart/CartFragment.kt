package com.example.producttask.ui.fragment.cart

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.producttask.R
import com.example.producttask.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
        setupAdapterListener()
    }

    private fun setupAdapterListener() {
        lifecycleScope.launch {
            cartAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.NotLoading -> {
                        if (cartAdapter.itemCount == 0)
                            showEmpty()
                        else
                            showData()
                    }
                }
            }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            cartViewModel.getCartLiveData().collect { data ->
                cartAdapter.submitData(data)
            }
        }
    }

    private fun showData() {
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