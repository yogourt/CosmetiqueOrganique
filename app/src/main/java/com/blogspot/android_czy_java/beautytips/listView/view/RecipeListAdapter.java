package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel;
import com.blogspot.android_czy_java.beautytips.detail.DetailActivity;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.TabletDetailViewModel;

import java.util.List;


public class RecipeListAdapter extends BaseListViewAdapter<RecipeModel> {

    public static final String KEY_ITEM = "item";

    static final int REQUEST_CODE_DETAIL_ACTIVITY = 50;


    public RecipeListAdapter(Context context, List<RecipeModel> list,
                             PositionListener positionListener,
                             ListViewViewModel viewModel,
                             float itemHeightDivider,
                             @Nullable TabletDetailViewModel tabletDetailViewModel) {

        super(context, list, positionListener, viewModel, tabletDetailViewModel);

        for (int i = 0; i < itemHeightsInDp.length; i++) {
            itemHeightsInDp[i] = (int) (itemHeightsInDp[i] / itemHeightDivider);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        if (viewType == VIEW_TYPE_HEADER) {
            itemView = inflater.inflate(R.layout.header_item_grid_view, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            itemView = inflater.inflate(R.layout.item_grid_view,
                    parent, false);
            return new ItemViewHolder(itemView);
        }
    }

    public class ItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {


        ItemViewHolder(View itemView) {
            super(itemView);
            mImage.setOnClickListener(this);
            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            RecipeModel recipe = list.get(getAdapterPosition() - 1);
            openDetailScreen(recipe, createSharedElementTransition());

        }
    }

    private void openDetailScreen(RecipeModel recipe, @Nullable Bundle sharedElementTransition) {

        //if the configuration is portrait, start detail activity
        if (!mContext.getResources().getBoolean(R.bool.is_tablet) ||
                mContext.getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT) {

            Bundle bundle = new Bundle();
            bundle.putLong(KEY_ID, recipe.getId());
            Intent detailActivityIntent = new Intent(mContext, DetailActivity.class);
            detailActivityIntent.putExtras(bundle);
            if (sharedElementTransition != null)
                ((Activity) mContext).startActivityForResult(detailActivityIntent,
                        REQUEST_CODE_DETAIL_ACTIVITY, sharedElementTransition);
            else ((Activity) mContext).startActivityForResult(detailActivityIntent,
                    REQUEST_CODE_DETAIL_ACTIVITY);
        }

        //for tablet landscape
        else tabletDetailViewModel.onRecipeClick(recipe.getId());
    }


}
