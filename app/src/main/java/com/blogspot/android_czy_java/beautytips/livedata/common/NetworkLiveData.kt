package com.blogspot.android_czy_java.beautytips.livedata.common

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData

// credits: https://android.jlelse.eu/connectivitylivedata-6861b9591bcc


class NetworkLiveData(private val connectivityManager: ConnectivityManager) : LiveData<Boolean>() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            postValue(true)

        }

        override fun onLost(network: Network?) {
            postValue(false)
        }
    }

    override fun onActive() {

        postValue(!connectivityManager.isDefaultNetworkActive)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    fun isConnection(): Boolean = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
}


