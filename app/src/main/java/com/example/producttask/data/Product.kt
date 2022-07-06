package com.example.producttask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.producttask.db.Converters
import java.util.*


@Entity(tableName = "products_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productID: Int,
    val productTitle: String,
    @TypeConverters(Converters::class)
    val dateAddedToCart: Date? = null
)