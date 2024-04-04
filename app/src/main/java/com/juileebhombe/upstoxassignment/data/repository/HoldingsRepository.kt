package com.juileebhombe.upstoxassignment.data.repository

import com.juileebhombe.upstoxassignment.data.api.NetworkService
import com.juileebhombe.upstoxassignment.data.local.DatabaseService
import com.juileebhombe.upstoxassignment.data.local.entity.Holding
import com.juileebhombe.upstoxassignment.data.model.toEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HoldingsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getHoldings(): Flow<List<Holding>> {
        return try {
            flow { emit(networkService.getHoldings()) }
                .map {
                    it.userHolding?.map { apiResponse -> apiResponse?.toEntity() }
                }.flatMapConcat { holdings ->
                    flow {
                        emit(
                            holdings?.filterNotNull()
                                ?.let { databaseService.deleteAllAndInsertAll(it) })
                    }
                }.flatMapConcat {
                    databaseService.getHoldings()
                }
        } catch (e: Exception) {
            throw Exception()
        }

    }

    fun getHoldingsFromDB(): Flow<List<Holding>> {
        return try {
            databaseService.getHoldings()
        } catch (e: Exception) {
            throw Exception()
        }
    }
}