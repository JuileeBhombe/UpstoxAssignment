package com.juileebhombe.upstoxassignment.di.module

import android.content.Context
import androidx.room.Room
import com.juileebhombe.upstoxassignment.data.api.NetworkService
import com.juileebhombe.upstoxassignment.data.local.AppDatabase
import com.juileebhombe.upstoxassignment.data.local.AppDatabaseService
import com.juileebhombe.upstoxassignment.data.local.DatabaseService
import com.juileebhombe.upstoxassignment.data.model.HoldingsDataModel
import com.juileebhombe.upstoxassignment.di.DatabaseName
import com.juileebhombe.upstoxassignment.ui.holdings.utils.HoldingsHelper
import com.juileebhombe.upstoxassignment.utils.AppConstants
import com.juileebhombe.upstoxassignment.utils.DefaultNetworkHelper
import com.juileebhombe.upstoxassignment.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideNetworkService(
    ): NetworkService {
        return object : NetworkService {
            override suspend fun getHoldings(): HoldingsDataModel {
                return withContext(Dispatchers.IO) {
                    val url = URL(AppConstants.ENDPOINT)
                    val httpURLConnection = url.openConnection() as HttpURLConnection

                    try {

                        val jsonString = httpURLConnection.inputStream.use {
                            it.bufferedReader().readText()
                        }

                        Json.decodeFromString<HoldingsDataModel?>(
                            JSONObject(jsonString).optJSONObject("data")?.toString() ?: "{}"
                        ) ?: HoldingsDataModel()

                    } catch (e: Exception) {
                        throw Exception()
                    }
                }
            }
        }
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = "holdings-database"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(appDatabase: AppDatabase): DatabaseService {
        return AppDatabaseService(appDatabase)
    }

    @Provides
    fun provideHelper(): HoldingsHelper {
        return HoldingsHelper()
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return DefaultNetworkHelper(context)
    }
}