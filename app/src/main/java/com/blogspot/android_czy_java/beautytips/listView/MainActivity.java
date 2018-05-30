package com.blogspot.android_czy_java.beautytips.listView;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final int NUM_COLUMNS_LAND = 2;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        prepareActionBar();
        prepareRecyclerView();
    }

    private void prepareActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
