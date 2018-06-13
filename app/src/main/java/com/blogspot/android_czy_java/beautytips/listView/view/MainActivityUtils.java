package com.blogspot.android_czy_java.beautytips.listView.view;

import android.content.res.Configuration;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class MainActivityUtils {

    public static final int NUM_COLUMNS_LAND = 2;
    public static final int NUM_COLUMNS_PORT = 1;



    public static StaggeredGridLayoutManager createLayoutManager(int orientation) {

        StaggeredGridLayoutManager layoutManager;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS_LAND,
                    StaggeredGridLayoutManager.VERTICAL);
        }
        else {
            layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS_PORT,
                    StaggeredGridLayoutManager.VERTICAL);
        };

        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        return layoutManager;
    }

}
