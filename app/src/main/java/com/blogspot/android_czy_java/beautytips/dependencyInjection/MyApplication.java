package com.blogspot.android_czy_java.beautytips.dependencyInjection;

import android.app.Activity;
import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerMyApplicationComponent.create().inject(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public DispatchingAndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}
