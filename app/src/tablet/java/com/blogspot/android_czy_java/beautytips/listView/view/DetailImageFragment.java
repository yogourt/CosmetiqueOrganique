package com.blogspot.android_czy_java.beautytips.listView.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailImageFragment extends Fragment {


    @BindView(R.id.image)
    ImageView mImageView;

    private TabletListViewViewModel viewModel;

    private TipListItem item;

    public DetailImageFragment() {
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

        View view = inflater.inflate(R.layout.fragment_detail_image, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getChosenTipLiveData().observe(getActivity(), new Observer<TipListItem>() {
            @Override
            public void onChanged(@Nullable TipListItem tipListItem) {
                if(tipListItem != null) {
                    item = tipListItem;
                }
                loadImage();
            }
        });
    }

    private void loadImage() {

        mImageView.setContentDescription(getResources()
                .getString(R.string.description_tip_image, item.getTitle()));

        Glide.with(this).
                setDefaultRequestOptions(new RequestOptions().dontTransform()).
                load(item.getImage()).
                into(mImageView);
    }

}
