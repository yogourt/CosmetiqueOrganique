package com.blogspot.android_czy_java.beautytips.view.listView.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel;
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys;
import com.blogspot.android_czy_java.beautytips.view.detail.DetailActivity;
import com.blogspot.android_czy_java.beautytips.view.listView.view.callback.RecipeListAdapterCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ItemViewHolder> {

    public static final String KEY_ITEM = "item";

    static final int REQUEST_CODE_DETAIL_ACTIVITY = 50;

    private int lastAnimatedPosition = -1;

    private List<RecipeModel> items;

    private RecipeListAdapterCallback activityCallback;

    public RecipeListAdapter(RecipeListAdapterCallback activityCallback, List<RecipeModel> list) {
        this.activityCallback = activityCallback;
        items = list;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.item_grid_view,
                parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        RecipeModel item = items.get(position);

        ViewCompat.setTransitionName(holder.mImage, item.getImageUrl());

        Context context = holder.itemView.getContext();
        Glide.with(context).
                setDefaultRequestOptions(new RequestOptions().dontTransform()).
                load(item.getImageUrl()).
                into(holder.mImage);

        holder.mTitle.setText(item.getTitle());
        holder.mImage.setContentDescription(context.getResources()
                .getString(R.string.description_tip_image, item.getTitle()));

        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastAnimatedPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(),
                    R.anim.item_animation_fall_down);
            viewToAnimate.startAnimation(animation);
            lastAnimatedPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void openDetailScreen(Context context, long recipeId, @Nullable Bundle sharedElementTransition) {

        //if the configuration is portrait, start detail activity
        if (!context.getResources().getBoolean(R.bool.is_tablet) ||
                context.getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT) {

            Bundle bundle = new Bundle();
            bundle.putLong(IntentDataKeys.KEY_RECIPE_ID, recipeId);
            Intent detailActivityIntent = new Intent(context, DetailActivity.class);
            detailActivityIntent.putExtras(bundle);
            if (sharedElementTransition != null)
                ((Activity) context).startActivityForResult(detailActivityIntent,
                        REQUEST_CODE_DETAIL_ACTIVITY, sharedElementTransition);
            else ((Activity) context).startActivityForResult(detailActivityIntent,
                    REQUEST_CODE_DETAIL_ACTIVITY);
        }

        //for tablet landscape
        else activityCallback.onRecipeClick(recipeId);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image)
        ImageView mImage;

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.scrim)
        View mScrim;

        @BindView(R.id.item_layout)
        CardView mCardView;

        @BindView(R.id.delete_tip_icon)
        ImageView mDeleteTipIcon;

        @BindView(R.id.heart_icon)
        ImageView mHeartIcon;


        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImage.setOnClickListener(this);
            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            RecipeModel recipe = items.get(getAdapterPosition());
            //openDetailScreen(view.getContext(), recipe.getId(), createSharedElementTransition());
            activityCallback.onRecipeClick(recipe.getId());

        }

        private Bundle createSharedElementTransition() {
            Pair<View, String> imagePair = new Pair<>(mImage, mImage.getTransitionName());
            Pair<View, String> scrimPair = new Pair<>(mScrim, mScrim.getTransitionName());
            return ActivityOptions.makeSceneTransitionAnimation((Activity) this.itemView.getContext(),
                    imagePair, scrimPair).toBundle();
        }
    }


}
