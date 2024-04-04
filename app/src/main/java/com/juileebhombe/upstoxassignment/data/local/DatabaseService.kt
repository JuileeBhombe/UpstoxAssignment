package com.juileebhombe.upstoxassignment.data.local

import com.juileebhombe.upstoxassignment.data.local.entity.Holding
import kotlinx.coroutines.flow.Flow

interface DatabaseService {

    fun getHoldings(): Flow<List<Holding>>

    fun deleteAllAndInsertAll(holdings: List<Holding>)

}