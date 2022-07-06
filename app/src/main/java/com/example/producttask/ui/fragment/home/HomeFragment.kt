package com.example.producttask.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.producttask.R
import com.example.producttask.data.Product
import com.example.producttask.databinding.FragmentHomeBinding
import com.example.producttask.utils.extentions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private lateinit var _context: Context

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    //no need to create shared view model
    private val homeViewModel by viewModels<HomeViewModel>()

    // create when need
    private val adapterProducts by lazy {
        AdapterProducts {
            // Heigher order Function or Interface
            addProductToCart(it)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        _context = requireContext()

        setupRecyclerView()
        setupObserver()
        setupOnClickListener()

    }

    private fun setupOnClickListener() {
        binding.tvEmpty.setOnClickListener(this)
    }

    private fun setupObserver() {
        homeViewModel.getProducts().observe(viewLifecycleOwner) { data ->
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
        adapterProducts.setAll(data)
        binding.apply {
            recyclerViewProducts.isVisible = true
            tvEmpty.isVisible = false
        }
    }

    private fun showEmpty() {
        binding.apply {
            recyclerViewProducts.isVisible = false
            tvEmpty.isVisible = true
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewProducts.apply {
            setHasFixedSize(true)
            adapter = adapterProducts
        }
    }


    private fun addProductToCart(id: Int) {
        homeViewModel.addToCard(id)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.tvEmpty.setOnClickListener(null)
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_empty -> addRandomData()
        }
    }

    private fun addRandomData() {
        _context.showToast("Random data added successfully")
        homeViewModel.insertData()
    }

}