package com.example.producttask.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.producttask.data.Product
import java.util.*

@Dao
interface ProductDao {

    @Query("Select * from products_table ")
     fun getAllProducts(): PagingSource<Int,Product>

    @Query("Select * from products_table where  dateAddedToCart BETWEEN :dateStart AND :dateEnd")
    fun getCart(dateStart: Date, dateEnd: Date): PagingSource<Int,Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    // add To Fav products
    @Query("UPDATE  products_table SET dateAddedToCart =:date WHERE productID = :id  ")
    suspend fun addToCart(id: Int, date: Date)

    // remove from  Fav products
    @Query("UPDATE  products_table SET dateAddedToCart='null' WHERE productID = :id  ")
    suspend fun removeFromCart(id: Int)

}