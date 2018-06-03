package com.blogspot.android_czy_java.beautytips.listView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.DetailActivity;
import com.blogspot.android_czy_java.beautytips.model.ListItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListViewAdapter extends FirebaseRecyclerAdapter<ListItem, ListViewAdapter.ViewHolder> {

    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE = "image";

    private Context mContext;

    ListViewAdapter(Context context, FirebaseRecyclerOptions<ListItem> options) {
        super(options);
        mContext = context;
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

        Glide.with(mContext).
                load(item.getImage()).
                into(holder.mImage);

        ViewCompat.setTransitionName(holder.mImage, item.getImage());
        holder.mTitle.setText(item.getTitle());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.image)
        ImageView mImage;

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.scrim)
        View mScrim;

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

            ListItem item = getItem(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, item.getTitle());
            bundle.putString(KEY_IMAGE, item.getImage());

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
