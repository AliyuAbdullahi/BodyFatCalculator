package com.lek.mybodyfatpercentage.common

import java.text.SimpleDateFormat
import java.util.Locale

fun formatLongToStringDate(date: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    return formatter.format(date)
}