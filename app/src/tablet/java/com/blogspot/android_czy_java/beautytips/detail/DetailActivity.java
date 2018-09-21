package com.blogspot.android_czy_java.beautytips.detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.AnalyticsUtils;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseItemActivity {

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.detail_scroll_view)
    ScrollView mScrollView;

    private DetailFragmentInterface detailFragment;

    private ViewTreeObserver.OnScrollChangedListener scrollListener;


    public interface DetailFragmentInterface {
        void addFav();

        void removeFav();

        int getIngredientLayoutHeight();

        void getFabState();

        Intent createDataIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        detailFragment = (DetailFragmentInterface)
                getSupportFragmentManager().findFragmentById(R.id.fragment_detail);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //this has to be done after onActivityCreated() in fragment is called
        prepareFab();
    }

    @Override
    public void onBackPressed() {

        setResult(RESULT_OK, detailFragment.createDataIntent());
        super.onBackPressed();
    }

    private void prepareFab() {
        if (scrollListener != null) mScrollView.getViewTreeObserver().
                removeOnScrollChangedListener(scrollListener);

        scrollListener = new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (mScrollView.getScrollY() < detailFragment.getIngredientLayoutHeight() +
                        getResources().getDimension(R.dimen.image_height)) {
                    mFab.show();
                } else {
                    mFab.hide();
                }
            }
        };
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(scrollListener);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFavouriteState();
            }
        });
        if (!(FirebaseAuth.getInstance().getCurrentUser() == null) &&
                !FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
            setFabInactive();
            detailFragment.getFabState();
        }
    }

    public void changeFavouriteState() {
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale);
        mFab.startAnimation(scaleAnim);

        if (FirebaseAuth.getInstance().getCurrentUser() == null ||
                FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
            SnackbarHelper.showFeatureForLoggedInUsersOnly(
                    getResources().getString(R.string.feature_favourites), mScrollView);
            return;
        }
        int bluegray700 = getResources().getColor(R.color.bluegray700);
        if (mFab.getImageTintList().getDefaultColor() == bluegray700) {
            setFabActive();
            detailFragment.addFav();
        } else {
            setFabInactive();
            detailFragment.removeFav();

        }
    }

    public void setFabActive() {
        mFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink200)));
        AnalyticsUtils.logEventNewLike(this);
    }

    private void setFabInactive() {
        mFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.bluegray700)));
    }

}

