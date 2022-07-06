package com.example.producttask.db

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun from(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    fun to(value: Long?): Date? {
        if (value == null)
            return null
        return Date(value)
    }
}