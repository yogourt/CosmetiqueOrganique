package com.blogspot.android_czy_java.beautytips.listView.view;


import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.RecyclerViewHelper;
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.SpacesItemDecoration;
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.DeleteTipDialog;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.TabletListViewViewModel;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.BaseListViewAdapter.KEY_FAV_NUM;
import static com.blogspot.android_czy_java.beautytips.listView.view.BaseListViewAdapter.KEY_ID;
import static com.blogspot.android_czy_java.beautytips.listView.view.BaseMainActivity.TAG_DELETE_TIP_DIALOG;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_ITEM;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements BaseListViewAdapter.PositionListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private StaggeredGridLayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;

    private TabletListViewViewModel viewModel;

    private AdLoader adLoader;
    private ArrayList<UnifiedNativeAd> ads = new ArrayList<>();

    private static final int NATIVE_AD_NUMBER = 10;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(TabletListViewViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        ButterKnife.bind(this, view);

        loadAds(view.getContext());

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //register this observer after layout is inflated
        viewModel.getRecyclerViewLiveData().observe(this, this::prepareRecyclerView);


        //this is done to pass changed fav num from detail fragment so it's updated in tip list
        viewModel.getTipChangeIndicator().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (getActivity() != null) {
                    Timber.d("activity not null");
                    if (getActivity().getIntent() != null) {
                        Bundle bundle = getActivity().getIntent().getExtras();
                        String id = bundle.getString(KEY_ID);
                        Long favNum = bundle.getLong(KEY_FAV_NUM, 0);

                        Timber.d("favNum: " + favNum);

                        if (!TextUtils.isEmpty(id)) {
                            if (mAdapter != null) mAdapter.setFavNum(id, favNum);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //open tip from notification
        if (getActivity() != null && getActivity().getIntent() != null) {
            String tipId = getActivity().getIntent().getStringExtra(KEY_ITEM);
            if (!TextUtils.isEmpty(tipId)) {
                mAdapter.openTipWithId(tipId);
                getActivity().setIntent(null);
            }
        }
    }

    private void prepareRecyclerView(List<ListItem> recyclerViewList) {

        if (loadingIndicator.getVisibility() == View.VISIBLE) {
            loadingIndicator.setVisibility(View.INVISIBLE);
        }

        float itemDivider;

        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        int orientation = getResources().getConfiguration().orientation;

        if (!isTablet) {
            itemDivider = 1;
        } else {
            boolean isSmallTablet = getResources().getBoolean(R.bool.is_small_tablet);
            //on tablet min720dp landscape
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                itemDivider = 2;
            }
            //on tablet min720dp portrait
            else itemDivider = 1.5f;

            if (isSmallTablet) itemDivider *= 0.8f;
        }


        List listWithAds = recyclerViewList;

        int offset = 7;

        Collections.shuffle(ads);

        for(UnifiedNativeAd ad: ads) {
            if (offset < recyclerViewList.size()) {
                listWithAds.add(offset, ad);
                offset += 7;
            }
        }
        listWithAds.add(0, new Object());


        mAdapter = new ListViewAdapter(getContext(), listWithAds, this,
                viewModel, itemDivider);


        mRecyclerView.setAdapter(mAdapter);

        int columnNum;

        if (!isTablet && orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnNum = 2;
        } else columnNum = 1;

        //add layout manager
        mLayoutManager = RecyclerViewHelper.createLayoutManager(columnNum);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //item decoration is added to make spaces between items in recycler view
        if (mRecyclerView.getItemDecorationCount() == 0)
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(
                    (int) getResources().getDimension(R.dimen.list_padding)));


    }

    private void loadAds(Context context) {

        AdLoader.Builder builder = new AdLoader.Builder(context, getString(R.string.native_ad_unit_id));
        adLoader = builder.forUnifiedNativeAd(
                unifiedNativeAd -> {
                    ads.add(unifiedNativeAd);
                }).build();

        adLoader.loadAds(new AdRequest.Builder().build(), NATIVE_AD_NUMBER);
    }

    public void setTipIdFromDynamicLink(String tipId) {
        if (mAdapter != null) mAdapter.openTipWithId(tipId);
    }



    /*
        Interface methods
     */

    @Override
    public void onClickDeleteTip(String tipId) {
        DialogFragment mDialogFragment = new DeleteTipDialog();
        ((DeleteTipDialog) mDialogFragment).setTipId(tipId);
        ((DeleteTipDialog) mDialogFragment).setViewModel(viewModel);
        mDialogFragment.show(getActivity().getFragmentManager(), TAG_DELETE_TIP_DIALOG);

    }
}
