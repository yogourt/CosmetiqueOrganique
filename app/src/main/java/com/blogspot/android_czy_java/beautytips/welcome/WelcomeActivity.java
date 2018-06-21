package com.blogspot.android_czy_java.beautytips.welcome;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.blogspot.android_czy_java.beautytips.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    public static final int RESULT_LOG_IN = 100;
    public static final int RESULT_LOG_IN_ANONYMOUSLY = 200;
    public static final int RESULT_TERMINATE = 300;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);
        prepareActionBar();
    }

    private void prepareActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ripple_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_TERMINATE);
        super.onBackPressed();
    }

    public void onLogInButtonClicked(View view) {
        setResult(RESULT_LOG_IN);
        finish();
    }

    public void onNegativeButtonClicked(View view) {
        setResult(RESULT_LOG_IN_ANONYMOUSLY);
        finish();
    }
}
