package com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils;

import android.content.res.Configuration;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class RecyclerViewHelper {

    public static StaggeredGridLayoutManager createLayoutManager(int columnNum) {

        StaggeredGridLayoutManager layoutManager;
            layoutManager = new StaggeredGridLayoutManager(columnNum,
                    StaggeredGridLayoutManager.VERTICAL);


        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        return layoutManager;
    }

}
