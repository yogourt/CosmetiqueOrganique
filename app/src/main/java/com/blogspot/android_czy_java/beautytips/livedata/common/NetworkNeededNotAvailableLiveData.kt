package com.blogspot.android_czy_java.beautytips.livedata.common

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData

// credits: https://android.jlelse.eu/connectivitylivedata-6861b9591bcc
/*
    LiveData that post value only when @param isNetworkNeeded == true.
    The value states: isNetworkNeededAndNotAvailable
 */

class NetworkNeededNotAvailableLiveData(private val connectivityManager: ConnectivityManager) : LiveData<Boolean>() {

    private var isNetworkNeeded = false

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            postValue(false)

        }

        override fun onLost(network: Network?) {
            postValue(true)
        }
    }

    fun onNetworkNeeded() {
        isNetworkNeeded = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    fun onNetworkNotNeeded() {
        if(isNetworkNeeded) {
            isNetworkNeeded = false
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
        postValue(false)
    }

    override fun onActive() {
        super.onActive()
        if (isNetworkNeeded) {
            onNetworkNeeded()
        }

    }

    override fun onInactive() {
        super.onInactive()
        if (isNetworkNeeded) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
}
