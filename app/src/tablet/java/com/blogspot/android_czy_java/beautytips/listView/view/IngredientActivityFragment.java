package com.blogspot.android_czy_java.beautytips.listView.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientActivityFragment extends Fragment {


    private TabletListViewViewModel viewModel;
    private ListItem item;


    public IngredientActivityFragment() {
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
        return inflater.inflate(R.layout.fragment_ingredient_activity, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getChosenIngredientLiveData().observe(getActivity(), new Observer<ListItem>() {
            @Override
            public void onChanged(@Nullable ListItem item) {
                if (item != null) {
                    IngredientActivityFragment.this.item = item;
                }
            }
        });


    }
}
