package com.example.producttask.ui.fragment.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.producttask.data.Product
import com.example.producttask.databinding.ItemCartBinding
import com.example.producttask.utils.DiffCallback

class AdapterCart(private val onRemoveFromCart: (id: Int) -> Unit) :
    RecyclerView.Adapter<AdapterCart.CartViewHolder>() {

    private val listOfProducts = ArrayList<Product>()

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // constructor
        init {
            // set item clicked in view holder , no need to refresh action when recycling items
            binding.btnRemove.setOnClickListener {
                // when recycling don't to any thing
                if (adapterPosition == -1)
                    return@setOnClickListener
                onRemoveFromCart.invoke(listOfProducts[adapterPosition].productID)
            }
        }

        fun bindItem() {
            binding.tvProductTitle.text = listOfProducts[adapterPosition].productTitle
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

    override fun getItemCount() = listOfProducts.size

    fun setAll(data: List<Product>) {
        val diffResult =
            DiffUtil.calculateDiff(
                DiffCallback(
                    oldItems = this.listOfProducts,
                    newItems = data
                ), true
            )

        this.listOfProducts.apply {
            clear()
            addAll(data)
        }
        diffResult.dispatchUpdatesTo(this)
    }
}