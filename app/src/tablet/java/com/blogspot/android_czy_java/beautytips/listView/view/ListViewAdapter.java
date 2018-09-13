package com.blogspot.android_czy_java.beautytips.listView.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.ingredient.IngredientActivity;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;
import com.blogspot.android_czy_java.beautytips.detail.DetailActivity;

import java.io.Serializable;
import java.util.List;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_DETAIL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_INGREDIENT;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_OPENING;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;


public class ListViewAdapter extends BaseListViewAdapter {

    public static final String KEY_ITEM = "item";


    public ListViewAdapter(Context context, List<ListItem> list,
                           PositionListener positionListener, TabletListViewViewModel viewModel,
                           float itemHeightDivider) {
        super(context, list, positionListener, viewModel);

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

    //this method is used when dynamic link is passed
    @Override
    public void openTipWithId(String id) {

    }

    public class ItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {


        ItemViewHolder(View itemView) {
            super(itemView);
            mImage.setOnClickListener(this);
            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


            final TabletListViewViewModel tabletViewModel = (TabletListViewViewModel) viewModel;

            //this will happen via ViewModel. In the DetailActivityFragment the chosen tip's description
            //will be opened

            //if the recipe was chosen
            if (!tabletViewModel.getCategory().equals(CATEGORY_INGREDIENTS)) {

                TipListItem item = (TipListItem) list.get(getAdapterPosition() - 1);

                //if the configuration is portrait, start detail activity
                if (mContext.getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_ITEM, item);
                    Intent detailActivityIntent = new Intent(mContext, DetailActivity.class);
                    detailActivityIntent.putExtras(bundle);
                    mContext.startActivity(detailActivityIntent, createSharedElementTransition());
                }

                if (tabletViewModel.getChosenTip() != null &&
                        tabletViewModel.getChosenTip().equals(item)) return;
                tabletViewModel.setChosenTip(item);
                tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_DETAIL);


            }
            //if the ingredient was chosen
            else {

                ListItem item = list.get(getAdapterPosition() - 1);

                //if the configuration is portrait, start detail activity
                if (mContext.getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_ITEM, item);
                    Intent ingredientActivityIntent = new Intent(mContext, IngredientActivity.class);
                    ingredientActivityIntent.putExtras(bundle);
                    mContext.startActivity(ingredientActivityIntent, createSharedElementTransition());
                }

                if (tabletViewModel.getIsShowingIngredientFromRecipe())
                    tabletViewModel.setIsShowingIngredientFromRecipe(false);

                if (tabletViewModel.getChosenIngredient() != null &&
                        tabletViewModel.getChosenIngredient().equals(item)) return;
                (tabletViewModel).setChosenIngredient(item);
                tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_INGREDIENT);

            }
        }
    }
}
