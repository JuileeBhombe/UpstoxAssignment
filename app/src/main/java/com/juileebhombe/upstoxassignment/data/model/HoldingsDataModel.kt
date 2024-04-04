package com.juileebhombe.upstoxassignment.data.model

import com.juileebhombe.upstoxassignment.data.local.entity.Holding
import kotlinx.serialization.Serializable


@Serializable
data class HoldingsDataModel(
    val userHolding: List<UserHolding?>? = null,
)

@Serializable
data class UserHolding(
    val symbol: String? = null,
    val quantity: Int? = null,
    val ltp: Double? = null,
    val avgPrice: Double? = null,
    val close: Double? = null,
)


fun UserHolding.toEntity(): Holding {
    return Holding(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
}