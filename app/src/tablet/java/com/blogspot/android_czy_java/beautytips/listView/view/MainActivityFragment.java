package com.blogspot.android_czy_java.beautytips.listView.view;


import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.RecyclerViewHelper;
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.SpacesItemDecoration;
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.DeleteTipDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.BaseListViewAdapter.KEY_FAV_NUM;
import static com.blogspot.android_czy_java.beautytips.listView.view.BaseListViewAdapter.KEY_ID;
import static com.blogspot.android_czy_java.beautytips.listView.view.BaseMainActivity.TAG_DELETE_TIP_DIALOG;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_DETAIL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements BaseListViewAdapter.PositionListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private StaggeredGridLayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;

    private TabletListViewViewModel viewModel;

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

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //register this observer after layout is inflated
        viewModel.getRecyclerViewLiveData().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> list) {
                prepareRecyclerView(list);
            }
        });


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

    private void prepareRecyclerView(List<ListItem> recyclerViewList) {

        //add adapter

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mAdapter = new ListViewAdapter(getContext(), recyclerViewList, this,
                    viewModel, 2);
        } else mAdapter = new ListViewAdapter(getContext(), recyclerViewList, this,
                viewModel, 1.5f);


        mRecyclerView.setAdapter(mAdapter);

        //add layout manager
        int columnNum = 1;
        mLayoutManager = RecyclerViewHelper.createLayoutManager(columnNum);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //item decoration is added to make spaces between items in recycler view
        if (mRecyclerView.getItemDecorationCount() == 0)
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(
                    (int) getResources().getDimension(R.dimen.list_padding)));

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
