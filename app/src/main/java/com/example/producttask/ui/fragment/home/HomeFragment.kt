package com.example.producttask.ui.fragment.home


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.producttask.R
import com.example.producttask.databinding.FragmentHomeBinding
import com.example.producttask.utils.extentions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
        setupAdapterListener()
        setupOnClickListener()

    }

    private fun setupOnClickListener() {
        binding.tvEmpty.setOnClickListener(this)
    }

    private fun setupAdapterListener() {


        lifecycleScope.launch {
            adapterProducts.loadStateFlow.collectLatest { loadStates ->
                when (loadStates.refresh) {
                    is LoadState.NotLoading -> {
                        if (adapterProducts.itemCount == 0)
                            showEmpty()
                        else showData()
                    }
                }
            }
        }


    }

    private fun setupObserver() {
        lifecycleScope.launch {
            homeViewModel.getProducts().collect { data ->
                adapterProducts.submitData(data)
            }
        }
    }

    private fun showData() {
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