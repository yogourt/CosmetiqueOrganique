package com.blogspot.android_czy_java.beautytips.detail.view;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.android_czy_java.beautytips.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_ID;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_IMAGE;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_TITLE;

public abstract class BaseItemActivity extends AppCompatActivity {

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;


    @BindView(R.id.app_bar)
    Toolbar mToolbar;

    @BindView(R.id.image)
    ImageView mImageView;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.adView)
    AdView mAdView;

    public String mTitle;
    public String mImage;
    public String mId;

    private Handler adHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportPostponeEnterTransition();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mTitle = bundle.getString(KEY_TITLE);
            mImage = bundle.getString(KEY_IMAGE);
            mId = bundle.getString(KEY_ID);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadImage();
        prepareToolbar();
        prepareAdView();
    }

    private void loadImage() {
        mImageView.setTransitionName(mImage);

        Glide.with(this).
                setDefaultRequestOptions(new RequestOptions().dontTransform()).
                load(mImage).
                listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                            target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                }).
                into(mImageView);
    }

    private void prepareToolbar() {
        mCollapsingToolbarLayout.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ripple_back);
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Timber.d("orientation landscape");
            mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                        showAdView();
                    } else hideAdView();
                }
            });
        }
        //add is always visible in portrait mode
        else {
            showAdView();
            mAdView.setVisibility(View.VISIBLE);
            Timber.d("showing ad view");
        }

    }


    private void showAdView() {
        mAdView.animate()
                .alpha(1f)
                .setDuration(300)
                .start();
    }

    private void hideAdView() {
        mAdView.animate()
                .alpha(0f)
                .setDuration(300)
                .start();
    }

    private void prepareAdView() {

        adHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                return false;
            }
        });
        adHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onDestroy() {
        adHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
