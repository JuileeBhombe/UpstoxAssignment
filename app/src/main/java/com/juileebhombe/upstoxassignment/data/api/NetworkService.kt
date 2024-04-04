package com.juileebhombe.upstoxassignment.data.api

import com.juileebhombe.upstoxassignment.data.model.HoldingsDataModel
import javax.inject.Singleton


@Singleton
interface NetworkService {
    suspend fun getHoldings(): HoldingsDataModel
}