package com.idmikael.newstestapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun getFormat(date: String): Date {
    val fmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return fmt.parse(date)
}

fun Date.simpleDateFormat(): String {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(this)
}

fun Date.simpleTimeFormat(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
}