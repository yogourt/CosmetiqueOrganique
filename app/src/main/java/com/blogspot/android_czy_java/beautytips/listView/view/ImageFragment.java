package com.blogspot.android_czy_java.beautytips.listView.view;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.TabletListViewViewModel;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_DETAIL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {


    @BindView(R.id.image)
    ImageView mImageView;

    @BindView(R.id.title_text_view)
    TextView mTitle;

    @BindView(R.id.icon_back)
    ImageView mIconBack;

    private TabletListViewViewModel viewModel;

    private ListItem item;

    public ImageFragment() {
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

        if (viewModel.getCurrentDetailFragment().equals(TAG_FRAGMENT_DETAIL)) {
            prepareContent(viewModel.getChosenTip());

        } else {
            prepareContent(viewModel.getChosenIngredient());
        }
    }

    private void prepareContent(@Nullable ListItem listItem) {
        if (listItem == null) return;

        item = listItem;
        loadImage();

        mTitle.setText(item.getTitle());

        if (viewModel.getIsShowingIngredientFromRecipe()) {
            Timber.d("showing ingredient from recipe");
            mIconBack.setVisibility(View.VISIBLE);
            mIconBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_DETAIL);
                    viewModel.setIsShowingIngredientFromRecipe(false);
                }
            });
        }
    }


    private void loadImage() {

        Glide.with(ImageFragment.this).
                load(item.getImage()).
                into(mImageView);

        mImageView.setContentDescription(getResources()
                .getString(R.string.description_tip_image, item.getTitle()));
    }

}
