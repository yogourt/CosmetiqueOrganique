package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.detail.view.DetailActivity;
import com.blogspot.android_czy_java.beautytips.ingredient.view.IngredientActivity;
import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;

import java.util.List;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;


public class ListViewAdapter extends BaseListViewAdapter {


    public ListViewAdapter(Context context, List<ListItem> list, BaseListViewAdapter.
            PositionListener positionListener, ListViewViewModel viewModel) {
        super(context, list, positionListener, viewModel);
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

        for(ListItem listItem: list) {
            if (listItem.getId().equals(id)) {
                Bundle bundle = createTipBundle((TipListItem) listItem);
                Intent detailActivityIntent = new Intent(mContext, DetailActivity.class);
                detailActivityIntent.putExtras(bundle);
                detailActivityIntent.setAction(Intent.ACTION_MEDIA_SHARED);
                mContext.startActivity(detailActivityIntent);
            }
        }
    }

    private Bundle createTipBundle(TipListItem tip) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, tip.getTitle());
        bundle.putString(KEY_IMAGE, tip.getImage());
        bundle.putString(KEY_ID, tip.getId());
        bundle.putLong(KEY_FAV_NUM, tip.getFavNum());
        if (!TextUtils.isEmpty(tip.getAuthorId()))
            bundle.putString(KEY_AUTHOR, tip.getAuthorId());

        return bundle;
    }



    public class ItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {


        ItemViewHolder(View itemView) {
            super(itemView);
            mImage.setOnClickListener(this);
            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mImage.getDrawable() == null) {
                SnackbarHelper.showWaitForImageLoad(itemView);
                return;
            }

            ListItem item = list.get(getAdapterPosition() - 1);

            //open ingredient activity
            if(viewModel.getCategory().equals(CATEGORY_INGREDIENTS)) {
                Intent ingredientActivityIntent = new Intent(mContext, IngredientActivity.class);


                Bundle bundle = new Bundle();
                bundle.putString(KEY_TITLE, item.getTitle());
                bundle.putString(KEY_IMAGE, item.getImage());
                bundle.putString(KEY_ID, item.getId());
                ingredientActivityIntent.putExtras(bundle);
                mContext.startActivity(ingredientActivityIntent, createSharedElementTransition());
            }

            //open detail activity
            else {
                Intent detailActivityIntent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = createTipBundle((TipListItem)item);
                detailActivityIntent.putExtras(bundle);

                mContext.startActivity(detailActivityIntent, createSharedElementTransition());
            }
        }
    }

}
