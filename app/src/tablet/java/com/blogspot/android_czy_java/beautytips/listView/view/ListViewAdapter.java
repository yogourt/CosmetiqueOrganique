package com.blogspot.android_czy_java.beautytips.listView.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;

import java.util.List;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_DETAIL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_INGREDIENT;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_OPENING;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;


public class ListViewAdapter extends BaseListViewAdapter {


    public ListViewAdapter(Context context, List<ListItem> list,
                           PositionListener positionListener, TabletListViewViewModel viewModel,
                           boolean smallList) {
        super(context, list, positionListener, viewModel);

        if (smallList) {
            for (int i = 0; i < itemHeightsInDp.length; i++) {
                itemHeightsInDp[i] = itemHeightsInDp[i] / 2;
            }
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
                tabletViewModel.setChosenTip(item);
                tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_DETAIL);


            }
            //if the ingredient was chosen
            else {


                ListItem item = list.get(getAdapterPosition() - 1);
                (tabletViewModel).setChosenIngredient(item);
                tabletViewModel.setCurrentDetailFragmentLiveData(TAG_FRAGMENT_INGREDIENT);

            }
        }
    }
}
