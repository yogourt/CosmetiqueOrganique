package com.blogspot.android_czy_java.beautytips.listView;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.newTip.NewTipActivity;
import com.firebase.ui.auth.IdpResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ListViewAdapter.PositionListener{

    public static final int NUM_COLUMNS_LAND = 2;
    public static final int NUM_COLUMNS_PORT = 1;

    public static final String KEY_CATEGORY = "category";
    //public static final String KEY_POSITION = "position";

    public static final String CATEGORY_ALL = "all";
    public static final String CATEGORY_HAIR = "hair";
    public static final String CATEGORY_FACE = "face";
    public static final String CATEGORY_BODY = "body";


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

    private StaggeredGridLayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;
    private String category;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            category = savedInstanceState.getString(KEY_CATEGORY);
            Timber.d(String.valueOf(position));
        } else category = CATEGORY_ALL;

        Timber.d("On create");
        getLifecycle().addObserver(mLoginHelper);

        prepareActionBar();
        prepareRecyclerView();
        prepareNavigationDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.smoothScrollToPosition(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_CATEGORY, category);
        super.onSaveInstanceState(outState);
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

        //add adapter
        mAdapter = new ListViewAdapter(this, FirebaseHelper.createFirebaseRecyclerOptions(
                category), this);
        //this is done for listening for changes in data, they will be applied automatically by adapter.
        getLifecycle().addObserver(mAdapter);

        mRecyclerView.setAdapter(mAdapter);

        int orientation = getResources().getConfiguration().orientation;
        //add layout manager
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS_LAND,
                    StaggeredGridLayoutManager.VERTICAL);
        }
        else {
            mLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS_PORT,
                    StaggeredGridLayoutManager.VERTICAL);
        };

        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //item decoration is added to make spaces between items in recycler view
        mRecyclerView.addItemDecoration(new SpacesItemDecoration((
                (int) getResources().getDimension(R.dimen.list_padding)), orientation));
    }

    /*
      here we set navigation drawer actions. There is apparently some bug in recreate(),
      because drawer is not closing but stays open on recreating, so here everything is surrounded
      by onDrawerClosed() from DrawerListener.
    */
    private void prepareNavigationDrawer() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                        mDrawerLayout.closeDrawers();

                        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                            @Override
                            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                            }

                            @Override
                            public void onDrawerOpened(@NonNull View drawerView) {
                            }

                            @Override
                            public void onDrawerClosed(@NonNull View drawerView) {
                                switch ((item.getItemId())) {
                                    case R.id.nav_add_new:
                                        if(NetworkConnectionHelper.isInternetConnection(
                                                MainActivity.this)) {
                                            Intent intent = new Intent(getBaseContext(),
                                                    NewTipActivity.class);
                                            startActivity(intent);
                                        } else {
                                            NetworkConnectionHelper.showUnableToAddTip(
                                                    mRecyclerView);
                                        }
                                        break;

                                    case R.id.nav_log_out:
                                        if(NetworkConnectionHelper.isInternetConnection(
                                                MainActivity.this)) {
                                            mLoginHelper.logOut();
                                        } else {
                                            NetworkConnectionHelper.showUnableToLogOut(
                                                    mRecyclerView);
                                        }
                                        break;

                                    case R.id.nav_all:
                                        if(category.equals(CATEGORY_ALL)) break;
                                        category = CATEGORY_ALL;
                                        recreate();
                                        break;
                                    case R.id.nav_hair:
                                        if(category.equals(CATEGORY_HAIR)) break;
                                        category = CATEGORY_HAIR;
                                        recreate();
                                        break;
                                    case R.id.nav_face:
                                        if(category.equals(CATEGORY_FACE)) break;
                                        category = CATEGORY_FACE;
                                        recreate();
                                        break;
                                    case R.id.nav_body:
                                        if(category.equals(CATEGORY_BODY)) break;
                                        category = CATEGORY_BODY;
                                        recreate();
                                        break;

                                }
                            }

                            @Override
                            public void onDrawerStateChanged(int newState) {
                            }
                        });

                        return true;
                    }
                }
        );
    }

    @Override
    public void onClick(int position) {
        this.position = position;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //This method is called after user signed in or canceled signing in (depending on result code)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LoginHelper.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK) {
                mLoginHelper.signIn();
            } else {
                //if response is null the user canceled sign in flow using back button
                if (response == null) {
                    finish();
                }
            }
        }
    }
}
