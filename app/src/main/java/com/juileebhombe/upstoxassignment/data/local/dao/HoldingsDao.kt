package com.juileebhombe.upstoxassignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.juileebhombe.upstoxassignment.data.local.entity.Holding
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldingsDao {

    @Query("SELECT * FROM holdings")
    fun getAll(): Flow<List<Holding>>

    @Insert
    fun insertAll(articles: List<Holding>)

    @Query("DELETE FROM holdings")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(articles: List<Holding>) {
        deleteAll()
        return insertAll(articles)
    }

}