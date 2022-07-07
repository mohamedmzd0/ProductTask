package com.example.producttask.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.producttask.data.Product
import com.example.producttask.databinding.ItemMainProductsBinding
import com.example.producttask.utils.extentions.dateToString
import com.example.producttask.utils.extentions.getThreeDaysAgoDate
import java.util.*


class AdapterProducts(private val onAddItemToCart: (id: Int) -> Unit) :
    PagingDataAdapter<Product, AdapterProducts.ProductsViewHolder>(PRODUCT_DIFF_CALLBACK) {

    inner class ProductsViewHolder(private val binding: ItemMainProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // constructor
        private var threeDaysAgoDate: Date = getThreeDaysAgoDate()

        init {
            // set item clicked in view holder , no need to refresh action when recycling items
            binding.btnAddCart.setOnClickListener {
                // when recycling don't to any thing
                if (bindingAdapterPosition == -1)
                    return@setOnClickListener
                getItem(bindingAdapterPosition)?.productID?.let { it1 -> onAddItemToCart.invoke(it1) }
//                 updating ui will be done automatically because of using live data with room db

            }
        }

        fun bindItem() {
            binding.apply {
                tvProductTitle.text = getItem(bindingAdapterPosition)?.productTitle
                // if product added to cart hide button
                btnAddCart.isVisible =
                    getItem(bindingAdapterPosition)?.dateAddedToCart?.after(threeDaysAgoDate) != true
                tvAddedDate.isVisible =
                    getItem(bindingAdapterPosition)?.dateAddedToCart?.after(threeDaysAgoDate) == true
                if (getItem(bindingAdapterPosition)?.dateAddedToCart != null)
                    tvAddedDate.text =
                        "Added on Date \n ${getItem(bindingAdapterPosition)?.dateAddedToCart.dateToString()}"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ItemMainProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bindItem()
    }


    companion object {
        private val PRODUCT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.productID == newItem.productID

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}