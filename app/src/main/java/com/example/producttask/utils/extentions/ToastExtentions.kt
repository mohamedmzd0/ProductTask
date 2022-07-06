package com.example.producttask.utils.extentions

import android.content.Context
import android.widget.Toast

fun Context.showLongToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_LONG).show()
}

fun Context.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}