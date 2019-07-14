package com.blogspot.android_czy_java.beautytips.view.detail.firebase;



import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

public class DetailFirebaseHelper {

    private DetailViewInterface activity;
    private String tipId;

    public DetailFirebaseHelper(DetailViewInterface activity) {
        this.activity = activity;
    }

    public interface DetailViewInterface {
        void setFabActiveFromFirebaseHelper();
        void prepareContent(DataSnapshot dataSnapshot);
        void setAuthor(String username);
        void setAuthorPhoto(String photoUrl);
    }

    private String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void getFirebaseDatabaseData(String tipId) {

        this.tipId = tipId;
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
    }

    public void removeTipFromFavourites(long favNum) {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child(getUserId())
                .removeValue();
    }

    public void setFabState(String tipId) {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                .child(getUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Timber.d("userId = " + getUserId());
                        String value = String.valueOf(dataSnapshot.getValue());
                        if(!value.equals("null")) activity.setFabActiveFromFirebaseHelper();
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
