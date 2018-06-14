package com.blogspot.android_czy_java.beautytips.detail.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.detail.firebase.DetailFirebaseHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_AUTHOR;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_ID;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_IMAGE;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_TITLE;

public class DetailActivity extends AppCompatActivity implements
        DetailFirebaseHelper.DetailViewInterface {

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

    @BindView(R.id.description_text_view)
    TextView mDescTextView;

    @BindView(R.id.ingredient1)
    TextView mIngredient1;

    @BindView(R.id.ingredient2)
    TextView mIngredient2;

    @BindView(R.id.ingredient3)
    TextView mIngredient3;

    @BindView(R.id.ingredient4)
    TextView mIngredient4;

    @BindView(R.id.layout_author)
    View mAuthorLayout;

    @BindView(R.id.author_photo)
    CircleImageView mAuthorPhoto;

    @BindView(R.id.nickname_text_view)
    TextView mAuthorTv;

    private String mTitle;
    private String mImage;
    private String mAuthorId;
    private String mId;

    private DetailFirebaseHelper mFirebaseHelper;

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
            if(bundle.containsKey(KEY_AUTHOR)) mAuthorId = bundle.getString(KEY_AUTHOR);
            mId = bundle.getString(KEY_ID);

            mFirebaseHelper = new DetailFirebaseHelper(this, mId);

            loadImage();
            mFirebaseHelper.getFirebaseDatabaseData();
            prepareToolbar();
            prepareFab();
            mScrollView.smoothScrollTo(0, 0);
        }
        else {
            finish();
        }


    }

    public void prepareContent(@NonNull DataSnapshot dataSnapshot) {
        String description = (String) dataSnapshot.child("description").getValue();
        mDescTextView.setText(description);
        String ingredient1 = (String) dataSnapshot.child("ingredient1").getValue();
        if(!TextUtils.isEmpty(ingredient1)) {
            mIngredient1.setVisibility(View.VISIBLE);
            mIngredient1.setText(ingredient1);
        }
        String ingredient2 = (String) dataSnapshot.child("ingredient2").getValue();
        if(!TextUtils.isEmpty(ingredient2)) {
            mIngredient2.setVisibility(View.VISIBLE);
            mIngredient2.setText(ingredient2);
        }
        String ingredient3 = (String) dataSnapshot.child("ingredient3").getValue();
        if(!TextUtils.isEmpty(ingredient3)) {
            mIngredient3.setVisibility(View.VISIBLE);
            mIngredient3.setText(ingredient3);
        }
        String ingredient4 = (String) dataSnapshot.child("ingredient4").getValue();
        if(!TextUtils.isEmpty(ingredient4)) {
            mIngredient4.setVisibility(View.GONE);
            mIngredient4.setText(ingredient4);
        }


        if(!TextUtils.isEmpty(mAuthorId)) {
        String authorPhoto = (String) dataSnapshot.child("authorPhoto").getValue();
        Timber.d(authorPhoto);
            mFirebaseHelper.getNicknameFromDb(mAuthorId);
            Glide.with(this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.color.bluegray700))
                .load(authorPhoto)
                .into(mAuthorPhoto);
            mAuthorLayout.setVisibility(View.VISIBLE);
        } else {
            int padding = (int) getResources().getDimension(R.dimen.desc_padding);
            int topPadding = (int) getResources().getDimension(
                    R.dimen.desc_top_padding_no_author);
            mDescTextView.setPadding(padding, topPadding, padding, padding);
        }
    }

    public  void setAuthor(String nickname) {
        mAuthorTv.setText(nickname);
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

    }

    /*
      I want FAB to be visible when ingredients layout is visible, and when user scrolls lower,
      it hides
     */
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
        mFirebaseHelper.setFabState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*
        here is call to onBackPressed() to provide proper exit animation (for some reason it
        wasn't working without super.onBackPressed())
        */
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
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

    public void changeFavouriteState(View view) {
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale);
        mFab.startAnimation(scaleAnim);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            SnackbarHelper.showFeatureForLoggedInUsersOnly(
                    getResources().getString(R.string.feature_favourites), mScrollView);
            return;
        }
        int bluegray700 = getResources().getColor(R.color.bluegray700);
        if(mFab.getImageTintList().getDefaultColor() == bluegray700) {
            setFabActive();
            mFirebaseHelper.addTipToFavourites();

        } else {
            mFab.setImageTintList(ColorStateList.valueOf(bluegray700));
            mFirebaseHelper.removeTipFromFavourites();
        }
    }

    @Override
    public void setFabActive() {
        mFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink200)));
    }

}
