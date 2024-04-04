package com.juileebhombe.upstoxassignment.data.local

import com.juileebhombe.upstoxassignment.data.local.entity.Holding
import kotlinx.coroutines.flow.Flow

class AppDatabaseService constructor(private val appDatabase: AppDatabase) : DatabaseService {

    override fun getHoldings(): Flow<List<Holding>> {
        return appDatabase.holdingsDao().getAll()
    }

    override fun deleteAllAndInsertAll(holdings: List<Holding>) {
        appDatabase.holdingsDao().deleteAllAndInsertAll(holdings)
    }

}