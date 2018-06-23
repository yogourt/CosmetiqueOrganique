package com.blogspot.android_czy_java.beautytips.sync;

import android.content.Context;
import android.provider.SyncStateContract;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

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
    }

}
