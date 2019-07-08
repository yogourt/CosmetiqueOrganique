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
import com.blogspot.android_czy_java.beautytips.ingredient.IngredientActivity;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;
import com.blogspot.android_czy_java.beautytips.detail.DetailActivity;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.TabletListViewViewModel;

import java.util.List;

import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_DETAIL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_INGREDIENT;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;


public class ListViewAdapter extends BaseListViewAdapter {

    public static final String KEY_ITEM = "item";

    public static final int REQUEST_CODE_DETAIL_ACTIVITY = 50;


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
        for (ListItem listItem : list) {
            if (listItem.getId().equals(id)) {
                openDetailScreen((TipListItem) listItem, null);
            }
        }
    }

    public void setFavNum(String id, long favNum) {

        Timber.d("setFavNum: " + favNum);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                TipListItem item = ((TipListItem) list.get(i));
                item.setFavNum(favNum);
                list.set(i, item);
            }

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


            final TabletListViewViewModel tabletViewModel = (TabletListViewViewModel) viewModel;

            viewModel.detailScreenOpenTimesAfterPromptDialog++;
            //this will happen via ViewModel. In the DetailActivityFragment the chosen tip's description
            //will be opened

            //if the ingredient was chosen
            if (tabletViewModel.getCategory().equals(CATEGORY_INGREDIENTS)) {

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
                tabletViewModel.setChosenIngredient(item);
                tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_INGREDIENT);


            }
            //if the tip was chosen
            else {
                TipListItem item = (TipListItem) list.get(getAdapterPosition() - 1);
                Timber.d("chosen tip: " + String.valueOf(getAdapterPosition() - 1));
                openDetailScreen(item, createSharedElementTransition());

            }

        }
    }

    private void openDetailScreen(TipListItem item, @Nullable Bundle sharedElementTransition) {

        TabletListViewViewModel tabletViewModel = (TabletListViewViewModel) viewModel;
        //tabletViewModel.notifyTipChange();

        //if the configuration is portrait, start detail activity
        if (!mContext.getResources().getBoolean(R.bool.is_tablet) ||
                mContext.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_ITEM, item);
            Intent detailActivityIntent = new Intent(mContext, DetailActivity.class);
            detailActivityIntent.putExtras(bundle);
            if (sharedElementTransition != null)
                ((Activity)mContext).startActivityForResult(detailActivityIntent,
                        REQUEST_CODE_DETAIL_ACTIVITY, sharedElementTransition);
            else ((Activity)mContext).startActivityForResult(detailActivityIntent,
                    REQUEST_CODE_DETAIL_ACTIVITY);
        }

        //else show recipe in detail fragment
        if (tabletViewModel.getChosenTip() != null &&
                tabletViewModel.getChosenTip().equals(item)) return;
        tabletViewModel.setChosenTip(item);
        tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_DETAIL);

    }


}
