package com.blogspot.android_czy_java.beautytips.detail.firebase;


import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailFirebaseHelper {

    private static String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private DetailViewInterface activity;
    private String tipId;

    public DetailFirebaseHelper(DetailViewInterface activity, String tipId) {
        this.activity = activity;
        this.tipId = tipId;
    }

    public interface DetailViewInterface {
        void setFabActive();
        void prepareContent(DataSnapshot dataSnapshot);
    }

    public void getFirebaseDatabaseData() {
        FirebaseDatabase.getInstance().getReference("tips/" + tipId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        activity.prepareContent(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void addTipToFavourites() {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child(userId)
                .setValue(true);
    }

    public void removeTipFromFavourites() {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child(userId)
                .removeValue();
    }

    public void setFabState() {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = String.valueOf(dataSnapshot.getValue());
                        if(!value.equals("null")) activity.setFabActive();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
