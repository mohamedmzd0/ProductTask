package com.example.producttask.utils

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<T>(private val newItems: List<T?>?, private var oldItems: List<T?>?) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newItems?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems?.get(oldItemPosition) == newItems?.get(newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems?.get(oldItemPosition)?.equals(newItems?.get(newItemPosition)) == true
    }

}