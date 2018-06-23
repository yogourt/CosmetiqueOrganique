package com.blogspot.android_czy_java.beautytips.sync;

import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;


public class SyncJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {

        Timber.d("onStartJob()");
        //this call to Firebase is done for syncing purposes. Persistence of data is enabled in
        //MyApplication class, so this call will update the cache on the device for use without
        //internet.
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println(String.valueOf(dataSnapshot.getValue()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.d("Data is not cached");
                        Timber.d(databaseError.getMessage());
                    }
                });
        return false; //there is nothing to do more
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false; //don't retry job if it was interrupted
    }
}
