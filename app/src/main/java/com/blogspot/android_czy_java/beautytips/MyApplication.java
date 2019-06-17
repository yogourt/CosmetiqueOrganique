package com.blogspot.android_czy_java.beautytips;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        Timber.plant(new Timber.DebugTree());

        FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }

}
