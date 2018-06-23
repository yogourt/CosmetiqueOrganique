package com.blogspot.android_czy_java.beautytips.sync;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class SyncScheduleHelper {

    private static final String TAG_SYNC = "tag_for_syncing";
    private static final int INTERVAL_SEC = (int) TimeUnit.HOURS.toSeconds(20);
    private static final int FLEXTIME_SEC = (int) TimeUnit.HOURS.toSeconds(8);

    private static boolean sInitialized;


    private static void scheduleSync(Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job.Builder builder = dispatcher.newJobBuilder()
                .setService(SyncJobService.class)
                .setTag(TAG_SYNC)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(INTERVAL_SEC,
                        INTERVAL_SEC + FLEXTIME_SEC));

        dispatcher.mustSchedule(builder.build());
    }

    public static void initialize(Context context) {

        if(sInitialized) return;

        sInitialized = true;
        scheduleSync(context);
        immediateSync();
    }

    public static void immediateSync() {
        Timber.d("immediateSync()");
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Timber.d("data is cached");
                        System.out.println(String.valueOf(dataSnapshot.getValue()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.d("Data is not cached");
                        Timber.d(databaseError.getMessage());
                    }
                });
    }
}
