package com.blogspot.android_czy_java.beautytips.detail;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.AnalyticsUtils;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.detail.firebase.DetailFirebaseHelper;
import com.blogspot.android_czy_java.beautytips.ingredient.IngredientActivity;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;
import com.blogspot.android_czy_java.beautytips.listView.view.TabletListViewViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.BaseListViewAdapter.KEY_FAV_NUM;
import static com.blogspot.android_czy_java.beautytips.listView.view.BaseListViewAdapter.KEY_ID;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_ITEM;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_INGREDIENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailActivityFragment extends Fragment
        implements DetailFirebaseHelper.DetailViewInterface, DetailActivity.DetailFragmentInterface {

    @Nullable
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Nullable
    @BindView(R.id.detail_scroll_view)
    ScrollView mScrollView;

    @BindView(R.id.layout_ingredients)
    View mLayoutIngredients;

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

    @BindView(R.id.fav_text_view)
    TextView mFavTv;

    @BindView(R.id.source_text_view)
    TextView mSourceTv;

    @BindView(R.id.fb_share_button)
    ShareButton fbShareButton;

    @BindView(R.id.layout_share)
    View mLayoutShare;

    private String description;
    private TabletListViewViewModel viewModel;
    private TipListItem item;

    private DetailFirebaseHelper mFirebaseHelper;

    private ViewTreeObserver.OnScrollChangedListener scrollListener;

    private boolean isPortrait;

    public DetailActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isPortrait = getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT;

        if (!isPortrait)
            viewModel = ViewModelProviders.of(getActivity()).get(TabletListViewViewModel.class);

        mFirebaseHelper = new DetailFirebaseHelper(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail_activity,
                container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isPortrait) {
            if (getActivity() != null) {
                item = (TipListItem) getActivity().getIntent().getExtras().getSerializable(KEY_ITEM);
            }
        } else {
            item = viewModel.getChosenTip();
            if(item != null) prepareFab();
        }

        if (item == null) return;
        mFirebaseHelper.getFirebaseDatabaseData(item.getId());
        Timber.d("item id: " + item.getId());
        prepareFavNum();


    }

    @Override
    public void onPause() {

        if(getActivity() != null) {
            Timber.d("set intent");
            getActivity().setIntent(createDataIntent());
            if(getActivity().getIntent() != null) Timber.d("intent set");
        }

        super.onPause();
    }

    @Override
    public void onDestroyView() {

        Timber.d("on destroy view");

        if (mScrollView != null)
            mScrollView.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
        super.onDestroyView();
    }

    private void prepareFab() {

        if (scrollListener != null) mScrollView.getViewTreeObserver().
                removeOnScrollChangedListener(scrollListener);

        scrollListener = new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (mScrollView.getScrollY() < getIngredientLayoutHeight() +
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
            getFabState();
        }
    }


    public void changeFavouriteState() {
        Animation scaleAnim = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
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
            addFav();

        } else {
            setFabInactive();
            removeFav();

        }
    }

    public void setFabActive() {
        if (getContext() == null) return;
        mFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink200)));
        AnalyticsUtils.logEventNewLike(getContext());
    }

    private void setFabInactive() {
        mFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.bluegray700)));
    }


    /*
        DetailActivity.DetailFragmentInterface implementation
     */

    @Override
    public void getFabState() {
        mFirebaseHelper.setFabState(item.getId());
    }

    @Override
    public int getIngredientLayoutHeight() {
        return mLayoutIngredients.getHeight();
    }

    @Override
    public void removeFav() {
        item.favNum++;
        mFirebaseHelper.removeTipFromFavourites(item.favNum * (-1));
        prepareFavNum();
    }

    @Override
    public void addFav() {
        //here favNum is distracted, because favNum is negative
        item.favNum--;
        //but here it has to be positive, because that's the implementation in DetailFirebaseHelper
        mFirebaseHelper.addTipToFavourites(item.favNum * (-1));
        prepareFavNum();
    }

    @Override
    public Intent createDataIntent() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        if(item != null) {
            bundle.putLong(KEY_FAV_NUM, item.getFavNum());
            bundle.putString(KEY_ID, item.getId());
        }
        intent.putExtras(bundle);
        return intent;
    }



    /*
        DetailFirebaseHelper.DetailViewInterface implementation
     */

    @Override
    public void setFabActiveFromFirebaseHelper() {
        Timber.d("setFabActiveFromFbHelper");
        //if it's portrait, pass this data to activity (it handles FAB)
        if (isPortrait) {
            ((DetailActivity)getActivity()).setFabActive();
        }
        //else FAB is handled in this fragment
        else {
            setFabActive();
        }
    }

    @Override
    public void prepareContent(DataSnapshot dataSnapshot) {


        if (getActivity() == null) return;
        description = (String) dataSnapshot.child("description").getValue();

        //start share button prep as soon as description is assigned
        prepareShareButton();

        mDescTextView.setText(description);
        String ingredient1 = (String) dataSnapshot.child("ingredient1").getValue();
        if (!TextUtils.isEmpty(ingredient1)) {
            mIngredient1.setVisibility(View.VISIBLE);
            mIngredient1.setText(ingredient1);
        }
        String ingredient2 = (String) dataSnapshot.child("ingredient2").getValue();
        if (!TextUtils.isEmpty(ingredient2)) {
            mIngredient2.setVisibility(View.VISIBLE);
            mIngredient2.setText(ingredient2);
        }
        String ingredient3 = (String) dataSnapshot.child("ingredient3").getValue();
        if (!TextUtils.isEmpty(ingredient3)) {
            mIngredient3.setVisibility(View.VISIBLE);
            mIngredient3.setText(ingredient3);
        }
        String ingredient4 = (String) dataSnapshot.child("ingredient4").getValue();
        if (!TextUtils.isEmpty(ingredient4)) {
            mIngredient4.setVisibility(View.VISIBLE);
            mIngredient4.setText(ingredient4);
        }


        if (!TextUtils.isEmpty(item.getAuthorId())) {
            mAuthorLayout.setVisibility(View.VISIBLE);
            mFirebaseHelper.getAuthorPhotoFromDb(item.getAuthorId());
            mFirebaseHelper.getNicknameFromDb(item.getAuthorId());

            int marginTop = (int) getResources().getDimension(R.dimen.share_margin_top_with_author);
            int marginEnd = (int) getResources().getDimension(R.dimen.share_margin_end);
            int marginBottom = (int) getResources().getDimension(R.dimen.share_margin_bottom);
            mLayoutShare.setPadding(0, marginTop, marginEnd, marginBottom);
        } else {
            mAuthorLayout.setVisibility(View.GONE);
        }


        //set source if it's in database
        if (dataSnapshot.child("source").getValue() != null) {
            String source = String.valueOf(dataSnapshot.child("source").getValue());
            mSourceTv.setVisibility(View.VISIBLE);
            mSourceTv.setText(Html.fromHtml(getResources().getString(R.string.source_label, source)));
            mSourceTv.setMovementMethod(LinkMovementMethod.getInstance());
        }


        if (mScrollView != null) {
            mScrollView.smoothScrollTo(0, 0);
            mScrollView.scrollTo(0, 0);
        }
        makeIngredientsClickable();
    }

    private void prepareFavNum() {

        //fav num in the db is negative
        mFavTv.setText(getResources().getString(R.string.fav_label,
                String.valueOf(item.favNum * -1)));
        if (item.favNum != 0) {
            mFavTv.setVisibility(View.VISIBLE);
        } else mFavTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setAuthor(String username) {
        mAuthorTv.setText(username);
    }

    @Override
    public void setAuthorPhoto(String photoUrl) {
        Glide.with(this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.color.bluegray700_semi))
                .load(photoUrl)
                .into(mAuthorPhoto);
    }


    /*
        end of interface

     */

    private void makeIngredientsClickable() {
        FirebaseDatabase.getInstance().getReference("ingredientList").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ingredient : dataSnapshot.getChildren()) {
                            String ingredientTitle = String.valueOf(
                                    ingredient.child("title").getValue());

                            if (ingredientTitle == null) continue;

                            if (mIngredient1.getText() != null &&
                                    mIngredient1.getText().toString().toLowerCase()
                                            .equals(ingredientTitle.toLowerCase())) {
                                makeIngredientClickable(mIngredient1, ingredient);
                            } else if (mIngredient2.getText() != null &&
                                    mIngredient2.getText().toString().toLowerCase().
                                            equals(ingredientTitle.toLowerCase())) {
                                makeIngredientClickable(mIngredient2, ingredient);
                            } else if (mIngredient3.getText() != null &&
                                    mIngredient3.getText().toString().toLowerCase().
                                            equals(ingredientTitle.toLowerCase())) {
                                makeIngredientClickable(mIngredient3, ingredient);
                            } else if (mIngredient4.getText() != null &&
                                    mIngredient4.getText().toString().toLowerCase().
                                            equals(ingredientTitle.toLowerCase())) {
                                makeIngredientClickable(mIngredient4, ingredient);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    private void makeIngredientClickable(TextView ingredientView, final DataSnapshot ingredientData) {
        ingredientView.setPaintFlags(ingredientView.getPaintFlags() |
                Paint.UNDERLINE_TEXT_FLAG);
        ingredientView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ListItem ingredientItem = ingredientData.getValue(ListItem.class);
                String id = ingredientData.getKey();
                if (ingredientItem != null) {
                    ingredientItem.setId(id);
                }

                if (isPortrait) {
                    Intent ingredientActivityIntent = new Intent(getContext(),
                            IngredientActivity.class);
                    ingredientActivityIntent.putExtra(KEY_ITEM, ingredientItem);
                    ingredientActivityIntent.setAction(Intent.ACTION_VIEW);
                    startActivity(ingredientActivityIntent);

                } else {
                    viewModel.setIsShowingIngredientFromRecipe(true);
                    viewModel.setChosenIngredient(ingredientItem);
                    viewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_INGREDIENT);
                }


            }
        });
    }

    private void prepareShareButton() {

        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://cosmetique.page.link/?apn=com.blogspot.android_czy_java." +
                        "beautytips&link=https://cometique.com/" + item.getId() + "&ofl=https://play.google." +
                        "com/store/apps/details?id=com.blogspot.android_czy_java.beautytips&ipn=com." +
                        "blogspot.android_czy_java.beautytips&link=https://cometique.com/-1&ofl=https://" +
                        "play.google.com/store/apps/details?id=com.blogspot.android_czy_java.beautytips/"))
                .setDynamicLinkDomain("cosmetique.page.link")
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().
                        setTitle(item.getTitle()).
                        setImageUrl(Uri.parse(item.getImage())).
                        setDescription(description).build())
                .buildShortDynamicLink()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();

                            Timber.d(task.getResult().getPreviewLink().toString());
                            ShareLinkContent content = new ShareLinkContent.Builder()
                                    .setContentUrl(shortLink)
                                    .build();
                            fbShareButton.setShareContent(content);
                        } else {
                            // Error
                            // ...
                        }

                    }
                });
    }

}


