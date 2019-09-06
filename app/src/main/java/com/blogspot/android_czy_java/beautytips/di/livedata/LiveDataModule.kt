package com.blogspot.android_czy_java.beautytips.di.livedata

import android.content.Context
import android.net.ConnectivityManager
import com.blogspot.android_czy_java.beautytips.MyApplication
import com.blogspot.android_czy_java.beautytips.livedata.common.NetworkLiveData
import dagger.Module
import dagger.Provides

@Module
class LiveDataModule {

    @Provides
    fun provideNetworkLiveData(connectivityManager: ConnectivityManager) = NetworkLiveData(connectivityManager)

    @Provides
    fun provideConnectivityManager(context: MyApplication): ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}