package com.blogspot.android_czy_java.beautytips.listView.view;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.DetailActivityFragment;
import com.blogspot.android_czy_java.beautytips.ingredient.IngredientActivityFragment;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.TabletListViewViewModel;
import com.kobakei.ratethisapp.RateThisApp;


import static android.content.Intent.ACTION_SEARCH;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.REQUEST_CODE_DETAIL_ACTIVITY;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;

public class MainActivity extends BaseMainActivity implements OpeningFragment.OpeningFragmentActivity,
        IngredientActivityFragment.IngredientFragmentActivity {


    private FrameLayout mDetailContainer;

    public static final String TAG_FRAGMENT_OPENING = "fragment_opening";
    public static final String TAG_FRAGMENT_INGREDIENT = "fragment_ingredient";
    public static final String TAG_FRAGMENT_DETAIL = "fragment_detail";

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getAction() != null && getIntent().getAction().equals(ACTION_SEARCH)) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        final TabletListViewViewModel viewModel = ViewModelProviders.of(this).
                get(TabletListViewViewModel.class);


        if (getResources().getBoolean(R.bool.is_tablet) &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            fragmentManager = getSupportFragmentManager();

            mDetailContainer = findViewById(R.id.fragment_detail_container);


            viewModel.getCurrentDetailFragmentLiveData().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String fragmentTag) {
                    if (fragmentTag == null) return;

                    if (fragmentTag.equals(TAG_FRAGMENT_DETAIL)) {

                        if (viewModel.getIsShowingIngredientFromRecipe()) {
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.fade_in, R.anim.top_to_bottom)
                                    .replace(R.id.fragment_detail_container, new DetailActivityFragment(),
                                            TAG_FRAGMENT_DETAIL)
                                    .commit();

                            viewModel.setIsShowingIngredientFromRecipe(false);

                        }

                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.fade_in, R.anim.quick_fade_out)
                                .replace(R.id.fragment_detail_container, new DetailActivityFragment(),
                                        TAG_FRAGMENT_DETAIL)
                                .commit();

                    } else if (fragmentTag.equals(TAG_FRAGMENT_INGREDIENT)) {

                        if (viewModel.getIsShowingIngredientFromRecipe()) {
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.bottom_to_top, R.anim.fade_out)
                                    .replace(R.id.fragment_detail_container,
                                            new IngredientActivityFragment(), TAG_FRAGMENT_INGREDIENT)
                                    .commit();

                        } else {
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.fade_in, R.anim.quick_fade_out)
                                    .replace(R.id.fragment_detail_container,
                                            new IngredientActivityFragment(), TAG_FRAGMENT_INGREDIENT)
                                    .commit();
                        }

                    } else if (fragmentTag.equals(TAG_FRAGMENT_OPENING)) {

                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.fade_in, R.anim.quick_fade_out)
                                .replace(R.id.fragment_detail_container,
                                        new OpeningFragment(), TAG_FRAGMENT_OPENING)
                                .commit();
                    }
                }
            });
        }
        prepareRatingRequest();
    }

    @Override
    public void onBackPressed() {

        //when ingredient is launched by click on detail screen ingredients list, on back pressed
        //come back to this detail screen
        TabletListViewViewModel tabletViewModel = (TabletListViewViewModel) viewModel;
        if (tabletViewModel.getIsShowingIngredientFromRecipe()) {
            tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_DETAIL);
            tabletViewModel.setIsShowingIngredientFromRecipe(false);
        } else if (viewModel.getCategory().equals(CATEGORY_ALL) &&
                (!tabletViewModel.getCurrentDetailFragment()
                        .equals(TAG_FRAGMENT_OPENING))) {
            tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_OPENING);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //this is only for portrait screen orientation, the data is passed in the intent
        // to MainActivityFragment
        if (requestCode == REQUEST_CODE_DETAIL_ACTIVITY) {
            if (data != null)
            setIntent(data);

        }
    }

    @Override
    void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TabletListViewViewModel.class);
        ((TabletListViewViewModel) viewModel).init();
    }

    /*

    OpeningFragmentActivity interface method

     */

    @Override
    public void selectNavigationViewItem(int itemId) {
        mDrawerListener = new MyDrawerLayoutListener(this, viewModel,
                itemId);
        mDrawerLayout.addDrawerListener(mDrawerListener);
        mDrawerListener.onDrawerClosed(mDrawerLayout);
    }


    /*

    IngredientFragmentActivity interface method

     */

    @Override
    public void search(String query) {
        mSearchView.setIconified(false);
        mSearchView.setQuery(query, true);
    }



    private void prepareRatingRequest() {
        // Custom condition: 3 days and 4 launches
        RateThisApp.Config config = new RateThisApp.Config(3, 4);
        RateThisApp.init(config);
        // Monitor launch times and interval from installation
        RateThisApp.onCreate(this);
        // If the condition is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this, R.style.DialogStyle);
    }
}