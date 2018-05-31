package com.blogspot.android_czy_java.beautytips.listView;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public SpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = mSpace;
        outRect.bottom = mSpace*2;
        outRect.left = mSpace;

        int position = parent.getChildAdapterPosition(view);

        // Add top margin only for the first item to avoid double space between items
        if (position == 0) {
            outRect.top = mSpace*2;
        }
        if(parent.getLayoutManager() instanceof StaggeredGridLayoutManager && position == 1) {
            outRect.top = mSpace*2;
        }
    }
}