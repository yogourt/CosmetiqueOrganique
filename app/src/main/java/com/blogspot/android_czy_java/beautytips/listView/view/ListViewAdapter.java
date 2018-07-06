package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Service;
import android.arch.lifecycle.ViewModel;
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
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.detail.view.DetailActivity;
import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseHelper;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.DeleteTipDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_YOUR_TIPS;

public class ListViewAdapter extends FirebaseRecyclerAdapter<ListItem, ListViewAdapter.ViewHolder> {

    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_FAV_NUM = "favourites_number";

    public static final int[] itemHeights = {630, 600, 670, 650};

    private Context mContext;
    private int lastPosition;
    private PositionListener mPositionListener;
    private ListViewViewModel viewModel;

    ListViewAdapter(Context context, FirebaseRecyclerOptions<ListItem> options, PositionListener
                    positionListener, ListViewViewModel viewModel) {
        super(options);
        mContext = context;
        mPositionListener = positionListener;
        lastPosition = -1;
        this.viewModel = viewModel;
    }

    /*
      Interface implemented by activity hosting recycler view, to save clicked position and to
      restore it when coming back from detail screen
    */
    interface PositionListener {
        void onClickDeleteTip(String tipId);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_grid_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final ListItem item) {

        ViewGroup.LayoutParams params = holder.mCardView.getLayoutParams();
        params.height = itemHeights[position % 4];
        holder.mCardView.setLayoutParams(params);

        ViewCompat.setTransitionName(holder.mImage, item.getImage());

        Glide.with(mContext).
                setDefaultRequestOptions(RequestOptions.centerCropTransform()).
                load(item.getImage()).
                into(holder.mImage);

        holder.mTitle.setText(item.getTitle());
        holder.mImage.setContentDescription(mContext.getResources()
                .getString(R.string.description_tip_image, item.getTitle()));

        //set visibility of cross
        if(item.getAuthorId() != null && item.getAuthorId().equals(FirebaseLoginHelper.getUserId())) {
            holder.mDeleteTipIcon.setVisibility(View.VISIBLE);
            holder.mDeleteTipIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.mImage.getDrawable() != null) {
                        mPositionListener.onClickDeleteTip(item.getId());
                    }
                    else {
                        SnackbarHelper.showWaitForImageLoad(holder.itemView);
                    }
                }
            });
        } else holder.mDeleteTipIcon.setVisibility(View.INVISIBLE);

        //set visibility of heart
        if(item.inFav) holder.mHeartIcon.setVisibility(View.VISIBLE);
        else holder.mHeartIcon.setVisibility(View.INVISIBLE);

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImage.setOnClickListener(this);
            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(mImage.getDrawable() == null) {
                SnackbarHelper.showWaitForImageLoad(itemView);
                return;
            }

            viewModel.setIsSearchVisible(false);
            Context  context = view.getContext();
            Intent detailActivityIntent = new Intent(context, DetailActivity.class);

            ListItem item = getItem(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, item.getTitle());
            bundle.putString(KEY_IMAGE, item.getImage());
            bundle.putString(KEY_ID, item.getId());
            bundle.putLong(KEY_FAV_NUM, item.getFavNum());
            if( !TextUtils.isEmpty(item.getAuthorId()) ) bundle.putString(KEY_AUTHOR, item.getAuthorId());

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
