package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.view.DetailActivity;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewAdapter extends FirebaseRecyclerAdapter<ListItem, ListViewAdapter.ViewHolder> {

    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";
    public static final String KEY_AUTHOR = "author";

    public static final int[] itemHeights = {630, 670, 600, 700};

    private Context mContext;
    private int lastPosition;
    private PositionListener mPositionListener;

    ListViewAdapter(Context context, FirebaseRecyclerOptions<ListItem> options, PositionListener
                    positionListener) {
        super(options);
        mContext = context;
        mPositionListener = positionListener;
        lastPosition = -1;
    }

    /*
      Interface implemented by activity hosting recycler view, to save clicked position and to
      restore it when coming back from detail screen
    */
    interface PositionListener {
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_grid_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ListItem item) {

        ViewGroup.LayoutParams params = holder.mCardView.getLayoutParams();
        params.height = itemHeights[position % 4];
        holder.mCardView.setLayoutParams(params);

        ViewCompat.setTransitionName(holder.mImage, item.getImage());

        Glide.with(mContext).
                setDefaultRequestOptions(RequestOptions.centerCropTransform()).
                load(item.getImage()).
                into(holder.mImage);

        holder.mTitle.setText(item.getTitle());

        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.item_animation_fall_down);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.image)
        ImageView mImage;

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.scrim)
        View mScrim;

        @BindView(R.id.item_layout)
        CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImage.setOnClickListener(this);
            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context  context = view.getContext();
            Intent detailActivityIntent = new Intent(context, DetailActivity.class);

            //send position to activity to save it
            mPositionListener.onClick(getAdapterPosition());

            ListItem item = getItem(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, item.getTitle());
            bundle.putString(KEY_IMAGE, item.getImage());
            bundle.putString(KEY_ID, item.getId());
            if( !TextUtils.isEmpty(item.getAuthor()) ) bundle.putString(KEY_AUTHOR, item.getAuthor());

            detailActivityIntent.putExtras(bundle);

            Pair<View, String> imagePair = new Pair<>((View)mImage, mImage.getTransitionName());
            Pair<View, String> scrimPair = new Pair<>(mScrim, mScrim.getTransitionName());
            // Pair<View, String> titlePair = new Pair<>((View)mTitle, mTitle.getTransitionName());
            Bundle animation = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                    imagePair, scrimPair).toBundle();
            context.startActivity(detailActivityIntent, animation);
        }
    }
}
