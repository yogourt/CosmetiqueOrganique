package com.blogspot.android_czy_java.beautytips.newTip.firebase;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.newTip.model.TipDetailsItem;
import com.blogspot.android_czy_java.beautytips.newTip.model.TipListItem;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
            //set user's photo
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
                            Timber.e(databaseError.getMessage());
                        }
                    });
            //set user's nickname
            FirebaseDatabase.getInstance().getReference("userNicknames").child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            activity.setAuthorNickname(String.valueOf(dataSnapshot.getValue()));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Timber.e(databaseError.getMessage());
                        }
                    });
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

                /*
                  path to tip is negative number, so when loading the new ones are on top
                  (Firebase Database supports only ascending order)
                 */
                String tipPath = "-" + tipNumber;

                final DatabaseReference detailsReference = FirebaseDatabase.getInstance()
                        .getReference("tips/" + tipPath);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {

                    //save details: description, ingredients
                    TipDetailsItem details;
                    if(ingredients.size() < 2) {
                        details = new TipDetailsItem(description, ingredients.get(0));
                    } else if(ingredients.size() < 3) {
                        details = new TipDetailsItem(description, ingredients.get(0),
                                ingredients.get(1));
                    } else if(ingredients.size() < 4) {
                        details = new TipDetailsItem(description, ingredients.get(0),
                                ingredients.get(1), ingredients.get(2));
                    } else {
                        details = new TipDetailsItem(description, ingredients.get(0),
                                ingredients.get(1), ingredients.get(2), ingredients.get(3));
                    }
                    detailsReference.setValue(details);

                    //save new tip number
                    tipNumReference.setValue(tipNumber);

                    final DatabaseReference listReference = FirebaseDatabase.getInstance()
                            .getReference("tipList/" + tipPath);

                    //save title, category and author
                    TipListItem listItem = new TipListItem(title, category, user.getUid(),
                            System.currentTimeMillis() * (-1));
                    listReference.setValue(listItem);

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

                }
                //if user was null show an error
                else {
                    SnackbarHelper.showAddingTipError(activity.getLayout());
                }
                //delete reference to activity to prevent activity leak
                activity = null;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e(databaseError.getMessage());
                SnackbarHelper.showAddingTipError(activity.getLayout());
                activity = null;
            }
        });
    }
}
