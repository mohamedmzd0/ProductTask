package com.example.producttask.ui.fragment.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.producttask.data.Product
import com.example.producttask.databinding.ItemCartBinding
import com.example.producttask.utils.DiffCallback

class AdapterCart(private val onRemoveFromCart: (id: Int) -> Unit) :
    PagingDataAdapter<Product,AdapterCart.CartViewHolder>(CART_DIFF_CALLBACK) {

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // constructor
        init {
            // set item clicked in view holder , no need to refresh action when recycling items
            binding.btnRemove.setOnClickListener {
                // when recycling don't to any thing
                if (bindingAdapterPosition == -1)
                    return@setOnClickListener
                getItem(bindingAdapterPosition)?.productID?.let { it1 -> onRemoveFromCart.invoke(it1) }
            }
        }

        fun bindItem() {
            binding.tvProductTitle.text = getItem(bindingAdapterPosition)?.productTitle
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindItem()
    }

    companion object{
        val CART_DIFF_CALLBACK=object :DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.productID==newItem.productID
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem==newItem
            }

        }
    }
}