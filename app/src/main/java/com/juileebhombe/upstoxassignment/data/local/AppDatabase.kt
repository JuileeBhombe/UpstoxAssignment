package com.juileebhombe.upstoxassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juileebhombe.upstoxassignment.data.local.dao.HoldingsDao
import com.juileebhombe.upstoxassignment.data.local.entity.Holding

@Database(entities = [Holding::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}