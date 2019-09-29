package com.blogspot.android_czy_java.beautytips.view.recipe;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blogspot.android_czy_java.beautytips.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;




public class OpeningFragment extends Fragment {

    @BindView(R.id.facebook_image)
    ImageView mFacebookImage;

    @BindView(R.id.pinterest_image)
    ImageView mPinterestImage;

    @BindView(R.id.button_hair)
    TextView mHairButton;

    @BindView(R.id.button_face)
    TextView mFaceButton;

    @BindView(R.id.button_body)
    TextView mBodyButton;

    @BindView(R.id.button_favourites)
    TextView mFavouritesButton;

    @BindView(R.id.button_your_recipes)
    TextView mYourRecipesButton;

    @BindView(R.id.button_add_new)
    TextView mAddNewButton;


    OpeningFragmentActivity activity;

    public OpeningFragment() {
        // Required empty public constructor
    }

    interface OpeningFragmentActivity {
       void selectNavigationViewItem(int itemId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_opening, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activity = (OpeningFragmentActivity) getActivity();
        } catch (ClassCastException e) {e.printStackTrace();}
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(isDetached()) return;

        mFacebookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri facebookUri = Uri.parse("https://www.facebook.com/Cosmetique.Organique/");
                intent.setData(facebookUri);
                getActivity().startActivity(intent);
            }
        });

        mPinterestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri pinterestUri = Uri.parse("https://pl.pinterest.com/CosmetiqueOrganique/");
                intent.setData(pinterestUri);
                getActivity().startActivity(intent);

            }
        });

        mHairButton.setOnClickListener(createOnClickListener(R.id.nav_hair));

        mFaceButton.setOnClickListener(createOnClickListener(R.id.nav_face));

        mBodyButton.setOnClickListener(createOnClickListener(R.id.nav_body));

        mYourRecipesButton.setOnClickListener(createOnClickListener(R.id.nav_your_tips));

        mFavouritesButton.setOnClickListener(createOnClickListener(R.id.nav_favourites));

        mAddNewButton.setOnClickListener(createOnClickListener(R.id.nav_add_new));
    }

    private View.OnClickListener createOnClickListener(final int itemId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timber.d("Hair checked");
                activity.selectNavigationViewItem(itemId);
            }
        };
    }
}
