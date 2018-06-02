package com.blogspot.android_czy_java.beautytips.newTip;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.blogspot.android_czy_java.beautytips.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewTipActivity extends AppCompatActivity {

    @BindView(R.id.app_bar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tip);
        ButterKnife.bind(this);

        overridePendingTransition(R.anim.bottom_to_top, R.anim.fade_out);

        prepareToolbar();
    }

    private void prepareToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ripple_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.top_to_bottom);
        }
        return super.onOptionsItemSelected(item);
    }
}
