package com.blogspot.android_czy_java.beautytips.listView.firebase;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.view.MainActivity;
import com.firebase.ui.auth.AuthUI;
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

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import timber.log.Timber;

public class LoginHelper implements LifecycleObserver {

    public static final int RC_SIGN_IN = 123;
    //list of authentication providers
    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private DatabaseReference mUserPhotoReference;

    MainActivity activity;

    public LoginHelper(MainActivity activity) {
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void createAuthListener() {
        mAuthStateListener = createAuthStateListener(activity);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void addAuthStateListener() {
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void removeAuthStateListener() {
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private FirebaseAuth.AuthStateListener createAuthStateListener(final Activity activity) {
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    activity.startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(providers)
                            .setTheme(R.style.LoginStyle)
                            .setLogo(R.drawable.logo_semi)
                            .build(), RC_SIGN_IN);
                } else {
                    signIn();
                }
            }
        };
    }


    public void logOut() {
        mUserPhotoReference = null;
        mAuth.signOut();
    }

    public void signIn() {
        String nickname = mAuth.getCurrentUser().getDisplayName();
        activity.setNickname(nickname);

        mUserPhotoReference = FirebaseDatabase.getInstance().getReference("user-photos")
                .child(getUserId());
        mUserPhotoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String dbUserPhotoUrl = dataSnapshot.getValue().toString();
                activity.setUserPhoto(dbUserPhotoUrl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    String getUserId() {
        return mAuth.getCurrentUser().getUid();
    }

    public void saveUserPhoto(final Uri photoUri) {
        final StorageReference photoReference = FirebaseStorage.getInstance().getReference("user-photos")
                .child(getUserId());
                photoReference.putFile(photoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        photoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri storagePhotoUri) {
                                String storagePhotoString = storagePhotoUri.toString();
                                Timber.d(storagePhotoString);
                                mUserPhotoReference.setValue(storagePhotoString);
                                activity.setUserPhoto(storagePhotoString);
                            }
                        });
                    }
                });
    }
}