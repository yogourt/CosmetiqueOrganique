package com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


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

        //this is not needed when there is a header that takes full width
        /*if(orientation == Configuration.ORIENTATION_LANDSCAPE && position == 1) {
            outRect.top = mSpace*2;
        }*/
    }
}