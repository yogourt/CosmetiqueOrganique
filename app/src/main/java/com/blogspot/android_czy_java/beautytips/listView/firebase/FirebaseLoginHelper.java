package com.blogspot.android_czy_java.beautytips.listView.firebase;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.sync.SyncScheduleHelper;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
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

import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;

public class FirebaseLoginHelper {

    public static final int RC_SIGN_IN = 123;
    //list of authentication providers
    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    private FirebaseAuth mAuth;

    private DatabaseReference mUserPhotoReference;
    private DatabaseReference mNicknamesReference;

    private UploadTask mSavingUserPhotoTask;

    private MainViewInterface activity;

    private ListViewViewModel viewModel;


    public FirebaseLoginHelper(MainViewInterface activity, ListViewViewModel viewModel) {
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.viewModel = viewModel;
        mNicknamesReference = FirebaseDatabase.getInstance().getReference("userNicknames");
    }

    public interface MainViewInterface {
        void startActivityForResult(Intent intent, int requestCode);

        Context getContext();

        void setNickname(String nickname);

        void setUserPhoto(String url);

        void setIsPhotoSaving(boolean value);

        void showPickNicknameDialog();
    }

    public void showSignInScreen() {
        activity.startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .setTheme(R.style.LoginStyle)
                .setLogo(R.drawable.logo_semi)
                .build(), RC_SIGN_IN);
    }


    public void logOut() {
        mUserPhotoReference = null;
        mAuth.signOut();
        signInAnonymously();
    }

    /*
      The actual signing in is handled by FirebaseUI-auth and we are just listening for change to
      update listView UI
     */
    public void signIn() {
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                viewModel.setCategory(CATEGORY_ALL);
                viewModel.changeUserState(ListViewViewModel.USER_STATE_LOGGED_IN);

                //this listener has to be removed - to properly handled signing out (we don't want
                //this method to be called then)
                mAuth.removeAuthStateListener(this);
            }
        });

    }

    public void prepareNavDrawerHeader() {
        setNicknameInNavDrawer();

        mUserPhotoReference = FirebaseDatabase.getInstance().getReference("userPhotos")
                .child(getUserId());
        mUserPhotoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String dbUserPhotoUrl = dataSnapshot.getValue().toString();
                    activity.setUserPhoto(dbUserPhotoUrl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e(databaseError.getMessage());
            }
        });
    }

    public void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Timber.d("signInAnonymously()");
                viewModel.changeUserState(ListViewViewModel.USER_STATE_ANONYMOUS);
                viewModel.setCategory(CATEGORY_ALL);
                viewModel.notifyRecyclerDataHasChanged();
                //cache all data for use without network
                SyncScheduleHelper.immediateSync(activity.getContext());
            }
        });
    }

    private void setNicknameInNavDrawer() {
        if (mAuth.getCurrentUser() != null) {
            mNicknamesReference.child(getUserId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String nickname = String.valueOf(dataSnapshot.getValue());
                            if (nickname.equals("null")) {
                                activity.setNickname(activity.getContext()
                                        .getResources().getString(R.string.label_anonymous));
                            } else activity.setNickname(nickname);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        }
    }

    public void saveNickname(String nickname) {
        mNicknamesReference.child(getUserId()).setValue(nickname);
    }

    public static String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void saveUserPhoto(final Uri photoUri) {
        final StorageReference photoReference = FirebaseStorage.getInstance().getReference("userPhotos")
                .child(getUserId());
        mSavingUserPhotoTask = photoReference.putFile(photoUri);
        mSavingUserPhotoTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

    public void stopPreviousUserPhotoSaving() {
        if (mSavingUserPhotoTask != null && mSavingUserPhotoTask.isInProgress())
            mSavingUserPhotoTask.cancel();
        activity.setIsPhotoSaving(false);
    }

    public static boolean isUserNull() {
        return FirebaseAuth.getInstance().getCurrentUser() == null;
    }

    public static boolean isUserAnonymous() {
        return FirebaseAuth.getInstance().getCurrentUser().isAnonymous();
    }

}