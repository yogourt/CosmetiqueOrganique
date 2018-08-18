package com.blogspot.android_czy_java.beautytips.listView.view;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.android_czy_java.beautytips.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdBannerFragment extends Fragment {

    @BindView(R.id.adView)
    AdView mAdBanner;


    private Handler adHandler;

    public AdBannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_ad_banner, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        prepareAdView();
    }

    @Override
    public void onStop() {
        adHandler.removeCallbacksAndMessages(null);
        super.onStop();
    }

    private void prepareAdView() {

        Timber.d("ad view preparing");

        adHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdBanner.loadAd(adRequest);
                mAdBanner.setVisibility(View.VISIBLE);
                return false;
            }
        });
        adHandler.sendEmptyMessageDelayed(0, 1000);
    }

}
