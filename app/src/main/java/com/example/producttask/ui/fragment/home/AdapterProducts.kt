package com.example.producttask.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.producttask.data.Product
import com.example.producttask.databinding.ItemMainProductsBinding
import com.example.producttask.utils.DiffCallback
import com.example.producttask.utils.extentions.dateToString
import com.example.producttask.utils.extentions.getThreeDaysAgoDate
import java.util.*


class AdapterProducts(private val onAddItemToCart: (id: Int) -> Unit) :
    RecyclerView.Adapter<AdapterProducts.ProductsViewHolder>() {

    private val listOfProducts = ArrayList<Product>()

    inner class ProductsViewHolder(private val binding: ItemMainProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // constructor
        private var threeDaysAgoDate: Date = getThreeDaysAgoDate()

        init {
            // set item clicked in view holder , no need to refresh action when recycling items
            binding.btnAddCart.setOnClickListener {
                // when recycling don't to any thing
                if (adapterPosition == -1)
                    return@setOnClickListener
                onAddItemToCart.invoke(listOfProducts[adapterPosition].productID)
//                 updating ui will be done automatically because of using live data with room db

            }
        }

        fun bindItem() {
            binding.apply {
                tvProductTitle.text = listOfProducts[adapterPosition].productTitle
                // if product added to cart hide button
                btnAddCart.isVisible =
                    listOfProducts[adapterPosition].dateAddedToCart?.after(threeDaysAgoDate) != true
                tvAddedDate.isVisible =
                    listOfProducts[adapterPosition].dateAddedToCart?.after(threeDaysAgoDate) == true
                if (listOfProducts[adapterPosition].dateAddedToCart != null)
                    tvAddedDate.text =
                        "Added on Date \n ${listOfProducts[adapterPosition].dateAddedToCart.dateToString()}"
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

    override fun getItemCount() = listOfProducts.size

    fun setAll(data: List<Product>) {
        // check for new updates only
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