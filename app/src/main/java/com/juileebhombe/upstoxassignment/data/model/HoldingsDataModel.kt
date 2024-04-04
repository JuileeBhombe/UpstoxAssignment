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
    val ltp: Float? = null,
    val avgPrice: Float? = null,
    val close: Float? = null,
)
