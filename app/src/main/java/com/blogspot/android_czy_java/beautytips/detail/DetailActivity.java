package com.blogspot.android_czy_java.beautytips.detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.ListViewAdapter.KEY_IMAGE;
import static com.blogspot.android_czy_java.beautytips.listView.ListViewAdapter.KEY_TITLE;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView mImageView;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.detail_scroll_view)
    ScrollView mScrollView;

    @BindView(R.id.layout_ingredients)
    View mLayoutIngredients;

    @BindView(R.id.app_bar)
    Toolbar mToolbar;

    private String mTitle;
    private String mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        supportPostponeEnterTransition();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mTitle = bundle.getString(KEY_TITLE);
            mImage = bundle.getString(KEY_IMAGE);

            loadImage();
        }

        prepareToolbar();
        prepareFab();
        mScrollView.smoothScrollTo(0, 0);
    }

    private void loadImage() {
        mImageView.setTransitionName(mImage);

        Glide.with(this).
                setDefaultRequestOptions(RequestOptions.centerCropTransform()).
                load(mImage).
                listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        mImageView.setTransitionName(null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                            target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        mImageView.setTransitionName(null);
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

    }

    private void prepareFab() {

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (mScrollView.getScrollY() < mLayoutIngredients.getHeight()){
                            mFab.show();
                        } else {
                            mFab.hide();
                        }
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //to provide proper custom animation on return direct call to finishAfterTransition()
        // is needed
        if(item.getItemId() == android.R.id.home) {
            finish();
            overrideExitTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overrideExitTransition();
    }

    private void overrideExitTransition() {
        overridePendingTransition(R.anim.fade_in, R.anim.top_to_bottom);
    }
}
