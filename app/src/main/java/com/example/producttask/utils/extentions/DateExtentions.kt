package com.example.producttask.utils.extentions

import java.text.SimpleDateFormat
import java.util.*


// no need to create date every call , just single instance is okay
// ,but when your changes from 23:59 to 1 AM we need to re-create it
//private var threeDaysDate: Date? = null
fun getThreeDaysAgoDate(): Date {
//    if (threeDaysDate != null)
//        return threeDaysDate!!

    val calender = Calendar.getInstance()
    calender.add(Calendar.DAY_OF_YEAR, -3)
    return calender.time
//    threeDaysDate = calender.time
//    return threeDaysDate!!
}

private const val DATE_FORMATTER_PATTERN = "yyy-MM-dd"
private val simpleDateFormat = SimpleDateFormat(DATE_FORMATTER_PATTERN, Locale.US)


fun Date?.dateToString() = if (this == null) "" else simpleDateFormat.format(this)