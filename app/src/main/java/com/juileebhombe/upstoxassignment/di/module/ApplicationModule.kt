package com.juileebhombe.upstoxassignment.di.module

import com.juileebhombe.upstoxassignment.data.api.NetworkService
import com.juileebhombe.upstoxassignment.data.model.Error
import com.juileebhombe.upstoxassignment.data.model.HoldingsDataModel
import com.juileebhombe.upstoxassignment.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
                    val url = URL(AppConstants.URL)
                    val httpURLConnection = url.openConnection() as HttpURLConnection

                    try {

                        val jsonString = httpURLConnection.inputStream.use {
                            it.bufferedReader().readText()
                        }

                        Json.decodeFromString<HoldingsDataModel?>(
                            JSONObject(jsonString).optJSONObject("data")?.toString() ?: "{}"
                        ) ?: HoldingsDataModel()

                    } catch (e: Exception) {
                        HoldingsDataModel(
                            Error(
                                code = httpURLConnection.responseCode,
                                message = httpURLConnection.responseMessage
                            )
                        )
                    } finally {


                    }
                }
            }
        }
    }
}