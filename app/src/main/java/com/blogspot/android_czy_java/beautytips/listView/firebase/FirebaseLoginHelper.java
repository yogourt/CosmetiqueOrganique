package com.blogspot.android_czy_java.beautytips.listView.firebase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.ListViewViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

import static com.blogspot.android_czy_java.beautytips.listView.model.User.USER_STATE_ANONYMOUS;
import static com.blogspot.android_czy_java.beautytips.listView.model.User.USER_STATE_LOGGED_IN;
import static com.blogspot.android_czy_java.beautytips.listView.utils.LoginProvidersHelper.saveProvidedPhoto;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;

public class FirebaseLoginHelper {

    public static final int RC_SIGN_IN = 123;
    //list of authentication providers
    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().setPermissions(Arrays.
                    asList("public_profile")).build());

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
                .setLogo(R.drawable.withoutback)
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
                viewModel.changeUserState(USER_STATE_LOGGED_IN);

                final Uri photoUrl = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
                if (photoUrl != null) {

                    mUserPhotoReference = FirebaseDatabase.getInstance().getReference("userPhotos")
                            .child(getUserId());

                    //if user has no photo in db, take the photo from login provider else it would be
                    //set in prepareNavDrawerHeader() method
                    mUserPhotoReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (String.valueOf(dataSnapshot.getValue()).equals("null")) {
                                String newPhotoUrl = saveProvidedPhoto(photoUrl);
                                activity.setUserPhoto(newPhotoUrl);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }

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
                viewModel.changeUserState(USER_STATE_ANONYMOUS);
                viewModel.setCategory(CATEGORY_ALL);
                viewModel.notifyRecyclerDataHasChanged();
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


}