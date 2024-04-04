package com.juileebhombe.upstoxassignment.data.model

import kotlinx.serialization.Serializable


@Serializable
data class HoldingsDataModel(
    val error: Error? = null,
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
