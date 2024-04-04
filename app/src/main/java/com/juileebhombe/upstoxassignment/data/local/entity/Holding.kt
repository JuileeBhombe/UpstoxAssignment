package com.juileebhombe.upstoxassignment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "holdings")
data class Holding(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val symbol: String? = null,
    val quantity: Int? = null,
    val ltp: Double? = null,
    val avgPrice: Double? = null,
    val close: Double? = null,
)
