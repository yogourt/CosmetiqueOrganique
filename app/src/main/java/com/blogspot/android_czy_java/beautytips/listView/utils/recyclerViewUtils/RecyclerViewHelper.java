package com.blogspot.android_czy_java.beautytips.view.listView.utils.recyclerViewUtils;


import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class RecyclerViewHelper {

    public static StaggeredGridLayoutManager createLayoutManager(int columnNum) {

        StaggeredGridLayoutManager layoutManager;
            layoutManager = new StaggeredGridLayoutManager(columnNum,
                    StaggeredGridLayoutManager.VERTICAL);


        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        return layoutManager;
    }

}
