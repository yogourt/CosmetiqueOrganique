package com.blogspot.android_czy_java.beautytips.view.detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blogspot.android_czy_java.beautytips.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.AppBarLayout;

import butterknife.BindView;
import timber.log.Timber;


public abstract class BaseItemActivity extends AppCompatActivity {


    @BindView(R.id.app_bar)
    Toolbar mToolbar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.adView)
    AdView mAdView;

    private Handler adHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if it's landscape on tablet, then orientation has changed after opening detail activity
        //and this activity shoud be finished. Data will be shown in fragment instead.
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && getResources().getBoolean(R.bool.is_tablet)) {

            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        supportPostponeEnterTransition();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadImage();
        prepareToolbar();
        prepareAdView();
    }

    private void loadImage() {
        /*
        image.setTransitionName(image);

        Glide.with(this).
                setDefaultRequestOptions(new RequestOptions().dontTransform()).
                load(image).
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
                into(image);
                */
    }

    private void prepareToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ripple_back);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
    public boolean onOptionsItemSelected(MenuItem item) {

        /*
        here is call to onBackPressed() to provide proper exit animation (for some reason it
        wasn't working without super.onBackPressed())
        */
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    @Override
    protected void onDestroy() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                && adHandler != null)
            adHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
