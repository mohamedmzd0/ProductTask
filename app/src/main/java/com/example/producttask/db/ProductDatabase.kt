package com.example.producttask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.producttask.data.Product

@Database(entities = [Product::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        private const val DB_NAME = "event_database.db"

        fun getDatabase(context: Context): ProductDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            // here synchronised used for blocking the other thread
            // from accessing another while in a specific code execution.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}