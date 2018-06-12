package com.blogspot.android_czy_java.beautytips.newTip.firebase;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import timber.log.Timber;

public class NewTipFirebaseHelper implements LifecycleObserver {

    private NewTipViewInterface activity;

    public interface NewTipViewInterface {
        void setAuthorPhoto(String url);
        void setAuthorNickname(String nickname);
        LinearLayout getLayout();
    }

    public NewTipFirebaseHelper(NewTipViewInterface activity) {
        this.activity = activity;
    }

    public void setAuthorDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase.getInstance().getReference("userPhotos").child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String path = (String) dataSnapshot.getValue();
                            if(!TextUtils.isEmpty(path)) activity.setAuthorPhoto(path);
                            Timber.d(path);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Timber.e("Database error: " + databaseError.getMessage());
                        }
                    });
            activity.setAuthorNickname(user.getDisplayName());
        }
    }

    public void addTip(final String title, final ArrayList<String> ingredients, final String description,
                       final String category, final String imagePath) {


        final DatabaseReference tipNumReference = FirebaseDatabase.getInstance()
                .getReference("tipNumber");
        tipNumReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int tipNumber = Integer.valueOf(String.valueOf(dataSnapshot.getValue()));
                Timber.d(String.valueOf(tipNumber));
                tipNumber++;

                //save new tip number
                tipNumReference.setValue(tipNumber);
                String tipPath = tipNumber + "tip";

                final DatabaseReference listReference = FirebaseDatabase.getInstance()
                        .getReference("tipList/" + tipPath);
                //save title
                listReference.child("title").setValue(title);
                //save category
                listReference.child("category").setValue(category);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {
                    //save author
                    listReference.child("author").setValue(user.getDisplayName());

                    //save author's photo
                    FirebaseDatabase.getInstance().getReference("userPhotos/" + user.getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    listReference.child("authorPhoto").setValue(dataSnapshot
                                            .getValue());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Timber.d(databaseError.getMessage());
                                }
                            });
                }

                //save tip image
                final StorageReference imageReference = FirebaseStorage.getInstance()
                        .getReference().child("tip" + tipNumber + "-photo");
                imageReference.putFile(Uri.parse(imagePath))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                imageReference.getDownloadUrl().addOnSuccessListener(
                                        new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri storageImageUri) {
                                        String storageImageString = storageImageUri.toString();
                                        listReference.child("image")
                                                .setValue(storageImageString);
                                    }
                                });
                            }
                        });

                DatabaseReference detailsReference = FirebaseDatabase.getInstance()
                        .getReference("tips/" + tipPath);
                //save description
                detailsReference.child("description").setValue(description);
                //save ingredients
                detailsReference.child("ingredient1").setValue(ingredients.get(0));
                if(ingredients.size() > 1) {
                    detailsReference.child("ingredient2").setValue(ingredients.get(1));
                }
                if(ingredients.size() > 2) {
                    detailsReference.child("ingredient3").setValue(ingredients.get(2));
                }
                if(ingredients.size() > 3) {
                    detailsReference.child("ingredient4").setValue(ingredients.get(3));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e(databaseError.getMessage());
                SnackbarHelper.showAddingTipError(activity.getLayout());
            }
        });
    }
}
