package com.blogspot.android_czy_java.beautytips

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import com.blogspot.android_czy_java.beautytips.di.DaggerAppComponent

import com.google.firebase.database.FirebaseDatabase
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import javax.inject.Inject

import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber

class MyApplication : Application(), HasSupportFragmentInjector, HasActivityInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

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

    override fun activityInjector() = activityInjector


}
