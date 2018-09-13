package com.blogspot.android_czy_java.beautytips.detail;

import android.content.res.Configuration;
import android.os.Bundle;

import com.blogspot.android_czy_java.beautytips.R;

import butterknife.ButterKnife;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_ITEM;

public class DetailActivity extends BaseItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

    }
}
