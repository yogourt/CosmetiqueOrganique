package com.blogspot.android_czy_java.beautytips.view.recipe;

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
        outRect.bottom = mSpace * 2;
        outRect.top = mSpace * 2;

        int position = parent.getChildAdapterPosition(view);

        if (position == 0) {
            outRect.left = mSpace * 2;
        } else {
            outRect.left = mSpace;
        }

        //this is not needed when there is a header that takes full width
        /*if(orientation == Configuration.ORIENTATION_LANDSCAPE && position == 1) {
            outRect.top = mSpace*2;
        }*/
    }
}