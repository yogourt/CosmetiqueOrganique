package com.blogspot.android_czy_java.beautytips.view.listView.view;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.NetworkConnectionHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainActivityViewModel;
import com.blogspot.android_czy_java.beautytips.view.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.view.listView.utils.LanguageHelper;
import com.blogspot.android_czy_java.beautytips.view.listView.view.dialogs.NicknamePickerDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper.RC_PERMISSION_EXT_STORAGE;
import static com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper.RC_PHOTO_PICKER;
import static com.blogspot.android_czy_java.beautytips.view.listView.view.dialogs.NicknamePickerDialog.TAG_NICKNAME_DIALOG;
import static com.blogspot.android_czy_java.beautytips.view.newTip.view.NewTipActivity.KEY_TIP_NUMBER;
import static com.blogspot.android_czy_java.beautytips.view.newTip.view.NewTipActivity.RESULT_DATA_CHANGE;
import static com.blogspot.android_czy_java.beautytips.view.welcome.WelcomeActivity.RC_WELCOME_ACTIVITY;
import static com.blogspot.android_czy_java.beautytips.view.welcome.WelcomeActivity.RESULT_LOG_IN;
import static com.blogspot.android_czy_java.beautytips.view.welcome.WelcomeActivity.RESULT_LOG_IN_ANONYMOUSLY;
import static com.blogspot.android_czy_java.beautytips.view.welcome.WelcomeActivity.RESULT_TERMINATE;

public abstract class BaseMainActivity extends AppCompatActivity
        implements FirebaseLoginHelper.MainViewInterface,
        NicknamePickerDialog.NicknamePickerDialogListener {



    public static final String KEY_QUERY = "query";

    public static final String KEY_TOKEN_FLAG = "notification_token";
    private static final int RC_NEW_TIP_ACTIVITY = 150;


    @BindView(R.id.main_layout)
    FrameLayout layout;

    SearchView mSearchView;

    CircleImageView photoIv;
    TextView nicknameTv;


    boolean isPhotoSaving;

    private DialogFragment mDialogFragment;

    MainActivityViewModel viewModel;

    InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LanguageHelper.setLanguageToEnglish(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);



        if (savedInstanceState == null) {
            MobileAds.initialize(this, getResources().getString(R.string.add_mob_app_id));
        }

        prepareInterstitialAd();

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchManager searchManager = (SearchManager) BaseMainActivity.
                this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            mSearchView = (SearchView) searchItem.getActionView();
        }
        if (mSearchView != null && searchManager != null) {
            mSearchView.setSearchableInfo(searchManager.
                    getSearchableInfo(BaseMainActivity.this.getComponentName()));
            prepareSearchView();
        }
        return super.onCreateOptionsMenu(menu);
    }
    */


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mDialogFragment != null) {
            mDialogFragment.dismiss();
        }
        super.onSaveInstanceState(outState);
    }


    private boolean tokenWasSaved() {
        boolean tokenWaSaved = getPreferences(MODE_PRIVATE).getBoolean(KEY_TOKEN_FLAG, false);

        getPreferences(MODE_PRIVATE).edit().putBoolean(KEY_TOKEN_FLAG, true).apply();
        return tokenWaSaved;
    }

    //This method is called after user signed in or canceled signing in (depending on result code)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_WELCOME_ACTIVITY) {
            if (resultCode == RESULT_TERMINATE) {
                onBackPressed();
            } else if (resultCode == RESULT_LOG_IN) {

            } else if (resultCode == RESULT_LOG_IN_ANONYMOUSLY) {
                viewModel.logInAnonymously();
            }
        }

        if (requestCode == FirebaseLoginHelper.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                showPickNicknameDialog();
            } else {
                //if response is null the user canceled sign in flow using back button, so we sign
                //him anonymously.
                if (response == null) {
                    viewModel.logInAnonymously();
                }
            }
        }
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                if (!NetworkConnectionHelper.isInternetConnection(this)) {
                    SnackbarHelper.showUnableToAddImage(layout);
                } else {
                    Glide.with(this)
                            .load(R.drawable.placeholder)
                            .into(photoIv);
                    SnackbarHelper.showAddImageMayTakeSomeTime(layout);
                }
                isPhotoSaving = true;
            }
        }

        if (requestCode == RC_NEW_TIP_ACTIVITY && resultCode == RESULT_DATA_CHANGE) {
            SnackbarHelper.showNewTipVisibleSoon(layout);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == RC_PERMISSION_EXT_STORAGE) {
            ExternalStoragePermissionHelper.answerForPermissionResult(this, grantResults,
                    layout);
        }
    }

    private void prepareInterstitialAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.support_unit_ad_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
    }


   /*
       Here is the beginning of interfaces methods
     */



    /*
       implementation of MyDrawerLayoutListener.DrawerCreationInterface
     */


    @Override
    public Context getContext() {
        return this;
    }



    /*
      Implementation of FirebaseLoginHelper.MainViewInterface
     */

    @Override
    public void setNickname(String nickname) {
        nicknameTv.setText(nickname);
    }

    @Override
    public void setUserPhoto(String imageUri) {

        if(!isDestroyed())
        Glide.with(this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.color.bluegrey700))
                .load(imageUri)
                .into(photoIv);
    }

    @Override
    public void setIsPhotoSaving(boolean isPhotoSaving) {
        this.isPhotoSaving = isPhotoSaving;
    }

    @Override
    public void showPickNicknameDialog() {
        mDialogFragment = new NicknamePickerDialog();
        mDialogFragment.show(getFragmentManager(), TAG_NICKNAME_DIALOG);
    }

    /*
       Implementation of NicknamePickerDialog.NicknamePickerDialogListener
     */
    @Override
    public void onDialogSaveButtonClick(String nickname) {
        setNickname(nickname);
    }

    /*
        End of interfaces
     */

}
