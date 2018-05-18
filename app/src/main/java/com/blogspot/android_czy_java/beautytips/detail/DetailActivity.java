package com.blogspot.android_czy_java.beautytips.detail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        image.setImageResource(R.drawable.beauty);

        prepareToolbar();

        }

    private void prepareToolbar() {
        Typeface playfair = Typeface.createFromAsset(getAssets(),
                "PlayfairDisplay-Regular.ttf");
        mCollapsingToolbarLayout.setTitle("Lorem ipsum");
        mCollapsingToolbarLayout.setExpandedTitleTypeface(playfair);
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(playfair);

    }

}
