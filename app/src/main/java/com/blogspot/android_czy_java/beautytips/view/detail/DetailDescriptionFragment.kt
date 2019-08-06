package com.blogspot.android_czy_java.beautytips.view.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DescriptionFragmentViewModel

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DescriptionFragmentData

class DetailDescriptionFragment : DetailFragment() {

    @BindView(R.id.description_tv)
    lateinit var description: TextView

    @Inject
    lateinit var viewModel: DescriptionFragmentViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_description,
                container, false)
        ButterKnife.bind(this, view)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.descriptionLiveData.observe(this, Observer { this.render(it) })
        if (getRecipeId() != null)
            viewModel.init(getRecipeId()!!)


    }


    private fun render(uiModel: GenericUiModel<DescriptionFragmentData>) {
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                val data = uiModel.data
                description.text = data.description

            }
            is GenericUiModel.StatusLoading -> {

            }
            is GenericUiModel.LoadingError -> {

            }
        }
    }
}



/*
@Override
public void onPause() {

    if (getActivity() != null) {
        getActivity().setIntent(createDataIntent());
    }

    super.onPause();
}




}


private void prepareSource(String sourceData) {

    source.setText(sourceData);
    source.setVisibility(View.VISIBLE);
    source.setText(Html.fromHtml(getResources().getString(R.string.source_label, sourceData)));
    source.setMovementMethod(LinkMovementMethod.getInstance());
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
    int bluegray700 = getResources().getColor(R.color.bluegrey700);
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
    mFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.bluegrey700)));
}



    DetailActivity.DetailFragmentInterface implementation


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


    if (item != null) {
        bundle.putLong(KEY_FAV_NUM, item.getFavNum());
        bundle.putString(KEY_ID, item.getId());
    }

    intent.putExtras(bundle);
    return intent;
}






    DetailFirebaseHelper.DetailViewInterface implementation


@Override
public void setFabActiveFromFirebaseHelper() {
    Timber.d("setFabActiveFromFbHelper");
    //if it's portrait, pass this data to activity (it handles FAB)
    if (isPortrait || !isTablet) {
        ((DetailActivity) getActivity()).setFabActive();
    }
    //else FAB is handled in this fragment
    else {
        setFabActive();
    }
}

@Override
public void prepareContent(DataSnapshot dataSnapshot) {

    comments = new ArrayList<>();

    if (getActivity() == null) return;

    //start share button prep as soon as description is assigned
    prepareShareButton();


    description.setText(description);
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
            .setDefaultRequestOptions(new RequestOptions().placeholder(R.color.bluegrey700_semi))
            .load(photoUrl)
            .into(mAuthorPhoto);
}



    end of interface



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

            if (!isTablet || isPortrait) {
                Intent ingredientActivityIntent = new Intent(getContext(),
                        IngredientActivity.class);
                ingredientActivityIntent.putExtra(KEY_ITEM, ingredientItem);
                ingredientActivityIntent.setAction(Intent.ACTION_VIEW);
                startActivity(ingredientActivityIntent);

            } else {
                tabletViewModel.setIsShowingIngredientFromRecipe(true);
                tabletViewModel.setChosenIngredient(ingredientItem);
                tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_INGREDIENT);
            }


        }
    });
}

private void prepareShareButton() {

    mShareButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent shareDataIntent = new Intent(Intent.ACTION_SEND);
            shareDataIntent.setType("text/plain");
            shareDataIntent.putExtra(Intent.EXTRA_SUBJECT, item.getTitle());
            String sharedText = item.getTitle() + "\n\n" + description.getText();
            shareDataIntent.putExtra(Intent.EXTRA_TEXT, sharedText);

            startActivity(Intent.createChooser(shareDataIntent,
                    getString(R.string.share_via_label)));
        }
    });
}

*/


