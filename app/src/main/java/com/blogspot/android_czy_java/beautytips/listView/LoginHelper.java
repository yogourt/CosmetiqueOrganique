package com.blogspot.android_czy_java.beautytips.listView;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class LoginHelper implements LifecycleObserver {

    private static final int RC_SIGN_IN = 123;
    //list of authentication providers
    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public LoginHelper(MainActivity activity) {
        mAuth = FirebaseAuth.getInstance();
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
                if(user == null) {
                    activity.startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), RC_SIGN_IN);
                }
            }
        };
    }
}
