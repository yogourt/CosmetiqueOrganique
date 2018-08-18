package com.blogspot.android_czy_java.beautytips.listView.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;

import timber.log.Timber;

public class MainActivity extends BaseMainActivity {


    public static final String TAG_FRAGMENT_OPENING = "fragment_opening";
    public static final String TAG_FRAGMENT_INGREDIENT = "fragment_ingredient";
    public static final String TAG_FRAGMENT_DETAIL = "fragment_detail";

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabletListViewViewModel viewModel = ViewModelProviders.of(this).
                get(TabletListViewViewModel.class);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_detail_container, new OpeningFragment(),
                        TAG_FRAGMENT_OPENING)
                .commit();

        viewModel.getCurrentDetailFragmentLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String fragmentTag) {
                if(fragmentTag == null) return;

                if(fragmentTag.equals(TAG_FRAGMENT_DETAIL)) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_detail_container, new DetailActivityFragment(),
                                    TAG_FRAGMENT_DETAIL)
                            .runOnCommit(createRunnableToMakeItemsClickable())
                            .commit();

                } else if(fragmentTag.equals(TAG_FRAGMENT_INGREDIENT)) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_detail_container,
                                    new IngredientActivityFragment(), TAG_FRAGMENT_INGREDIENT)
                            .runOnCommit(createRunnableToMakeItemsClickable())
                            .commit();
                }
            }
        });
    }


    private Runnable createRunnableToMakeItemsClickable() {
        return new Runnable() {
            @Override
            public void run() {
                ((TabletListViewViewModel)viewModel).setAreListItemsClickable();
            }
        };
    }

    @Override
    void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TabletListViewViewModel.class);
        ((TabletListViewViewModel)viewModel).init();
    }

    @Override
    public void handleDynamicLink() {

    }
}