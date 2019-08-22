package com.blogspot.android_czy_java.beautytips

import android.app.Activity
import android.app.Application

import androidx.fragment.app.Fragment
import com.blogspot.android_czy_java.beautytips.di.DaggerAppComponent

import com.google.firebase.database.FirebaseDatabase
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import javax.inject.Inject

import dagger.android.support.HasSupportFragmentInjector
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor


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

        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .setDefaultFontPath("/Roboto-RobotoRegular.ttf")
                                .setFontAttrId(R.attr.fontPath).build()
                )).build())

        val component = DaggerAppComponent.builder()
                .application(this)
                .context(this)
                .build()
        component.inject(this)

    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun activityInjector() = activityInjector

}
