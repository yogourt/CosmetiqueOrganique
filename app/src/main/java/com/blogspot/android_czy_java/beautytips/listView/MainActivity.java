package com.blogspot.android_czy_java.beautytips.listView;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.newTip.NewTipActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    public static final int NUM_COLUMNS_LAND = 2;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Inject
    LoginHelper mLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getLifecycle().addObserver(mLoginHelper);

        prepareActionBar();
        prepareRecyclerView();
        prepareNavigationDrawer();
    }

    private void prepareActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private void prepareRecyclerView() {

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(NUM_COLUMNS_LAND,
                    StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(manager);
        }
        else {
            LinearLayoutManager manager = new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(manager);
        }
        ListViewAdapter adapter = new ListViewAdapter(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration((
                (int) getResources().getDimension(R.dimen.list_padding))));
    }

    private void prepareNavigationDrawer() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawerLayout.closeDrawers();
                        switch ((item.getItemId())) {
                            case R.id.nav_add_new:
                                Intent intent = new Intent(getBaseContext(), NewTipActivity.class);
                                startActivity(intent);
                                break;

                            case R.id.nav_log_out:
                                mLoginHelper.logOut();

                        }
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
