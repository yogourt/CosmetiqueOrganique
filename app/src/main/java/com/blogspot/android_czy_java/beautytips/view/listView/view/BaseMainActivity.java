package com.blogspot.android_czy_java.beautytips.view.listView.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainActivityViewModel;
import com.blogspot.android_czy_java.beautytips.view.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.view.listView.utils.LanguageHelper;
import com.blogspot.android_czy_java.beautytips.view.listView.view.dialogs.NicknamePickerDialog;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper.RC_PERMISSION_EXT_STORAGE;
import static com.blogspot.android_czy_java.beautytips.view.newTip.view.NewTipActivity.RESULT_DATA_CHANGE;

public abstract class BaseMainActivity extends AppCompatActivity {


    public static final String KEY_QUERY = "query";

    private static final int RC_NEW_TIP_ACTIVITY = 150;


    @BindView(R.id.main_layout)
    FrameLayout layout;

    SearchView mSearchView;

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

}
