package com.juileebhombe.upstoxassignment.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface NetworkHelper {
    fun isNetworkConnected(): Boolean
    fun observeNetworkConnectivity(): LiveData<Boolean>
}

class DefaultNetworkHelper(private val context: Context) : NetworkHelper {
    private val _networkConnected = MutableLiveData<Boolean>()

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateNetworkState()
        }
    }

    init {
        updateNetworkState()

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
    }

    override fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    override fun observeNetworkConnectivity(): LiveData<Boolean> {
        return _networkConnected
    }

    private fun updateNetworkState() {
        _networkConnected.value = isNetworkConnected()
    }

}