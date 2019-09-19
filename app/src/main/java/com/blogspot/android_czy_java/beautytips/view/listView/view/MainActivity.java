package com.blogspot.android_czy_java.beautytips.view.listView.view;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.view.common.RecipeListFragment;
import com.blogspot.android_czy_java.beautytips.view.detail.DetailDescriptionFragment;
import com.blogspot.android_czy_java.beautytips.view.ingredient.IngredientActivityFragment;
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel;
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel;
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainActivityViewModel;
import com.kobakei.ratethisapp.RateThisApp;


import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.content.Intent.ACTION_SEARCH;

public class MainActivity extends BaseMainActivity implements
        IngredientActivityFragment.IngredientFragmentActivity {


    public static final String TAG_FRAGMENT_OPENING = "fragment_opening";
    public static final String TAG_FRAGMENT_INGREDIENT = "fragment_ingredient";
    public static final String TAG_FRAGMENT_DETAIL = "fragment_detail";

    public static final String KEY_FIRST_OPEN = "first open";

    private FragmentManager fragmentManager;

    @Inject
    MainActivityViewModel viewModel;

    @Inject
    DetailActivityViewModel detailActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getAction() != null && getIntent().getAction().equals(ACTION_SEARCH)) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        AndroidInjection.inject(this);

        fragmentManager = getSupportFragmentManager();

        if (getResources().getBoolean(R.bool.is_tablet) &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {


            detailActivityViewModel.getCurrentDetailFragmentLiveData().observe(this, fragmentTag -> {
                if (fragmentTag == null) return;

                switch (fragmentTag) {
                    case TAG_FRAGMENT_DETAIL:

                        if (detailActivityViewModel.getIsShowingIngredientFromRecipe()) {
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.fade_in, R.anim.top_to_bottom)
                                    .replace(R.id.fragment_detail_container, new DetailDescriptionFragment(),
                                            TAG_FRAGMENT_DETAIL)
                                    .commit();

                            detailActivityViewModel.setIsShowingIngredientFromRecipe(false);

                        }

                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.fade_in, R.anim.quick_fade_out)
                                .replace(R.id.fragment_detail_container, new DetailDescriptionFragment(),
                                        TAG_FRAGMENT_DETAIL)
                                .commit();

                        break;
                    case TAG_FRAGMENT_INGREDIENT:

                        if (detailActivityViewModel.getIsShowingIngredientFromRecipe()) {
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

                        break;
                    case TAG_FRAGMENT_OPENING:

                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.fade_in, R.anim.quick_fade_out)
                                .replace(R.id.fragment_detail_container,
                                        new OpeningFragment(), TAG_FRAGMENT_OPENING)
                                .commit();
                        break;
                }
            });
        }
        prepareRatingRequest();

        getPreferences(MODE_PRIVATE).edit().putBoolean(KEY_FIRST_OPEN, false).apply();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (viewModel.shouldInterstitialAdBeShown() && getInterstitialAd().isLoaded()) {
            showInterstitialAd();
            viewModel.setDetailScreenOpenTimesAfterInterstitialAd(0);
        }
    }




    @Override
    public void search(String query) {
        getMSearchView().setIconified(false);
        getMSearchView().setQuery(query, true);
    }


    private void prepareRatingRequest() {
        RateThisApp.Config config = new RateThisApp.Config(3, 4);
        RateThisApp.init(config);
        RateThisApp.onCreate(this);
        RateThisApp.showRateDialogIfNeeded(this, R.style.DialogStyle);
    }

    private void showInterstitialAd() {
        getInterstitialAd().show();
    }


    @Override
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}