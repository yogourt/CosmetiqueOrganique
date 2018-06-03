package com.blogspot.android_czy_java.beautytips.dependencyInjection;

import android.app.Activity;
import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerMyApplicationComponent.create().inject(this);

        Timber.plant(new Timber.DebugTree());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public DispatchingAndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}
