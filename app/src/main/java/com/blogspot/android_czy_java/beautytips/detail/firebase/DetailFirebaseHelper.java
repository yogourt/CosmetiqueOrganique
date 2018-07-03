package com.blogspot.android_czy_java.beautytips.detail.firebase;


import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

public class DetailFirebaseHelper {

    private DetailViewInterface activity;
    private String tipId;

    public DetailFirebaseHelper(DetailViewInterface activity, String tipId) {
        this.activity = activity;
        this.tipId = tipId;
    }

    public interface DetailViewInterface {
        void setFabActive();
        void prepareContent(DataSnapshot dataSnapshot);
        void setAuthor(String username);
        void setAuthorPhoto(String photoUrl);
    }

    private String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
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

    public void addTipToFavourites(long favNum) {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child(getUserId())
                .setValue(true);
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child("favNum")
                .setValue(favNum * -1);
    }

    public void removeTipFromFavourites(long favNum) {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child(getUserId())
                .removeValue();
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child("favNum")
                .setValue(favNum * -1);
    }

    public void setFabState() {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child(getUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Timber.d("userId = " + getUserId());
                        String value = String.valueOf(dataSnapshot.getValue());
                        if(!value.equals("null")) activity.setFabActive();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public void getNicknameFromDb(String userId) {
        FirebaseDatabase.getInstance().getReference("userNicknames/" + userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        activity.setAuthor(String.valueOf(dataSnapshot.getValue()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.d(databaseError.getMessage());
                    }
                });
    }

    public void getAuthorPhotoFromDb(String userId) {
        FirebaseDatabase.getInstance().getReference("userPhotos/" + userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String photoUrl = String.valueOf(dataSnapshot.getValue());
                        if(!photoUrl.equals("null")) activity.setAuthorPhoto(photoUrl);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.d(databaseError.getMessage());
                    }
                });
    }
}
