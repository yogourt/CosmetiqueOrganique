package com.blogspot.android_czy_java.beautytips

import android.app.Application

import androidx.fragment.app.Fragment
import com.blogspot.android_czy_java.beautytips.di.DaggerAppComponent

import com.google.firebase.database.FirebaseDatabase
import com.squareup.leakcanary.LeakCanary

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber

class MyApplication : Application(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate() {
        super.onCreate()

        /*
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        */


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        FirebaseDatabase.getInstance().reference.keepSynced(true)

        val component = DaggerAppComponent.builder()
                .application(this)
                .context(this)
                .build()
        component.inject(this)

    }

    override fun supportFragmentInjector() = fragmentInjector

}
