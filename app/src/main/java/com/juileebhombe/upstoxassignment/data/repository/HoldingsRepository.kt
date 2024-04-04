package com.juileebhombe.upstoxassignment.data.repository

import com.juileebhombe.upstoxassignment.data.api.NetworkService
import com.juileebhombe.upstoxassignment.data.model.HoldingsDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HoldingsRepository @Inject constructor(private val networkService: NetworkService) {
    suspend fun getHoldings(): HoldingsDataModel {
        return networkService.getHoldings()
    }
}