package com.blogspot.android_czy_java.beautytips.listView.view;


import android.app.Service;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;

import butterknife.BindView;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;

public class MainActivity extends BaseMainActivity {


    private static final String TAG_FRAGMENT_INGREDIENT = "fragment_ingredient";
    private static final String TAG_FRAGMENT_DETAIL = "fragment_detail";
    FragmentManager fragmentManager;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabletListViewViewModel viewModel = ViewModelProviders.of(this).
                get(TabletListViewViewModel.class);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_detail_container, new OpeningFragment())
                .commit();

        viewModel.getChosenTipLiveData().observe(this, new Observer<TipListItem>() {
            @Override
            public void onChanged(@Nullable TipListItem item) {
                if(item != null && fragmentManager.findFragmentByTag(TAG_FRAGMENT_DETAIL) == null) {

                    Timber.d("begin transaction");
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_detail_container, new DetailActivityFragment(),
                                    TAG_FRAGMENT_DETAIL)
                            .commit();
                }
            }
        });
        viewModel.getChosenIngredientLiveData().observe(this, new Observer<ListItem>() {
            @Override
            public void onChanged(@Nullable ListItem item) {
                if(item != null && fragmentManager.
                        findFragmentByTag(TAG_FRAGMENT_INGREDIENT) == null) {

                    Timber.d("begin transaction");
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_detail_container,
                                    new IngredientActivityFragment(), TAG_FRAGMENT_INGREDIENT)
                            .commit();
                }
            }
        });
    }

    @Override
    public void handleDynamicLink() {

    }
}