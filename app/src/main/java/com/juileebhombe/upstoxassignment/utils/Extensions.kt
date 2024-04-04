package com.juileebhombe.upstoxassignment.utils

import com.juileebhombe.upstoxassignment.data.model.UserHolding
import java.text.DecimalFormat

fun UserHolding.calculateTotalPNL(): Double? {
    return quantity?.let { ltp?.times(it) }
}

fun Double.isNegative(): Boolean {
    return if (this < 0) true else false
}

fun Double.formatCost(): String {
    val decimalFormat = DecimalFormat("#,##0.00")
    val formattedNumber = decimalFormat.format(this)
    return if (this >= 0) "\u20B9$formattedNumber" else "-\u20B9${formattedNumber.substring(1)}"
}