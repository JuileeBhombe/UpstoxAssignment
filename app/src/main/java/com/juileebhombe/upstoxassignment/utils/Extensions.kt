package com.juileebhombe.upstoxassignment.utils

import java.text.DecimalFormat


fun String.isNegative(): Boolean {
    return this.startsWith("-")
}

fun Double.formatCost(): String {
    val decimalFormat = DecimalFormat("#,##0.00")
    val formattedNumber = decimalFormat.format(this)
    return if (this >= 0) "\u20B9$formattedNumber" else "-\u20B9${formattedNumber.substring(1)}"
}

fun Double.formatPercentage(decimalPlaces: Int): String {
    return String.format("%.${decimalPlaces}f", this) + "%"
}