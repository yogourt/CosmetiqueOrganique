package com.blogspot.android_czy_java.beautytips.welcome;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.NetworkConnectionHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    public static final int RC_WELCOME_ACTIVITY = 200;

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
        if(NetworkConnectionHelper.isInternetConnection(this)) {
            setResult(RESULT_LOG_IN);
            finish();
        }
        else SnackbarHelper.showUnableToLogIn(mToolbar);
    }

    public void onNegativeButtonClicked(View view) {
        if(NetworkConnectionHelper.isInternetConnection(this)) {
            setResult(RESULT_LOG_IN_ANONYMOUSLY);
            finish();
        }
        else SnackbarHelper.showUnableToLogInAnonymously(mToolbar);
    }
}
