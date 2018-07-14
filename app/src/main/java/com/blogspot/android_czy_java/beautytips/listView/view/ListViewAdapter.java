package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.detail.view.DetailActivity;
import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel.ORDER_NEW;
import static com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel.ORDER_POPULAR;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_BODY;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_FACE;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_HAIR;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;


public class ListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int CONST_HEIGHT = 1920;
    public static final int CONST_HEIGHT_LAND = 1080;

    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_FAV_NUM = "favourites_number";

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    public static final int[] itemHeightsInDp = {630, 600, 670, 650};

    private Context mContext;
    private int lastPosition;
    private PositionListener mPositionListener;
    private List<ListItem> list;
    private ListViewViewModel viewModel;

    ListViewAdapter(Context context, List<ListItem> list, PositionListener
            positionListener, ListViewViewModel viewModel) {
        mContext = context;
        this.list = list;
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

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder h, int position) {


        //add header with chips that takes whole width of the list
        if (h.getItemViewType() == VIEW_TYPE_HEADER) {
            HeaderViewHolder holder = (HeaderViewHolder) h;
            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }

        //fill in normal list item
        else if (h.getItemViewType() == VIEW_TYPE_ITEM) {
            final ItemViewHolder holder = (ItemViewHolder) h;
            final ListItem item = list.get(position - 1);
            ViewGroup.LayoutParams params = holder.mCardView.getLayoutParams();

            //here item height in pixels is calculated from height in dp
            int itemHeight;
            int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
            if (mContext.getResources().getConfiguration()
                    .orientation == Configuration.ORIENTATION_PORTRAIT) {
                itemHeight = itemHeightsInDp[position % 4] * screenHeight / CONST_HEIGHT;
            } else {
                itemHeight = itemHeightsInDp[position % 4] * screenHeight / CONST_HEIGHT_LAND;
            }

            params.height = itemHeight;
            holder.mCardView.setLayoutParams(params);

            ViewCompat.setTransitionName(holder.mImage, item.getImage());

            Glide.with(mContext).
                    setDefaultRequestOptions(new RequestOptions().dontTransform()).
                    load(item.getImage()).
                    into(holder.mImage);

            holder.mTitle.setText(item.getTitle());
            holder.mImage.setContentDescription(mContext.getResources()
                    .getString(R.string.description_tip_image, item.getTitle()));

            //set visibility of cross
            if (item.getAuthorId() != null && !FirebaseLoginHelper.isUserNull()
                    && item.getAuthorId().equals(FirebaseLoginHelper.getUserId())) {
                holder.mDeleteTipIcon.setVisibility(View.VISIBLE);
                holder.mDeleteTipIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.mImage.getDrawable() != null) {
                            mPositionListener.onClickDeleteTip(item.getId());
                        } else {
                            SnackbarHelper.showWaitForImageLoad(holder.itemView);
                        }
                    }
                });
            } else holder.mDeleteTipIcon.setVisibility(View.INVISIBLE);

            //set visibility of heart
            if (item.inFav) holder.mHeartIcon.setVisibility(View.VISIBLE);
            else holder.mHeartIcon.setVisibility(View.INVISIBLE);

            setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        //list size + header
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_HEADER;
        else return VIEW_TYPE_ITEM;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.item_animation_fall_down);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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

            if (mImage.getDrawable() == null) {
                SnackbarHelper.showWaitForImageLoad(itemView);
                return;
            }

            Context context = view.getContext();
            Intent detailActivityIntent = new Intent(context, DetailActivity.class);

            ListItem item = list.get(getAdapterPosition() - 1);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, item.getTitle());
            bundle.putString(KEY_IMAGE, item.getImage());
            bundle.putString(KEY_ID, item.getId());
            bundle.putLong(KEY_FAV_NUM, item.getFavNum());
            if (!TextUtils.isEmpty(item.getAuthorId()))
                bundle.putString(KEY_AUTHOR, item.getAuthorId());

            detailActivityIntent.putExtras(bundle);

            Pair<View, String> imagePair = new Pair<>((View) this.mImage, mImage.getTransitionName());
            Pair<View, String> scrimPair = new Pair<>(mScrim, mScrim.getTransitionName());
            Bundle animation = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                    imagePair, scrimPair).toBundle();
            context.startActivity(detailActivityIntent, animation);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.chip_cloud)
        ChipCloud mChipCloud;

        @BindView(R.id.switch_popular)
        TextView mSwitchPopular;

        @BindView(R.id.switch_new)
        TextView mSwitchNew;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            String category = viewModel.getCategory();
            if (category.equals(CATEGORY_HAIR) || category.equals(CATEGORY_BODY)
                    || category.equals(CATEGORY_FACE) || category.equals(CATEGORY_INGREDIENTS)) {
                mChipCloud.setVisibility(View.VISIBLE);
                String[] chipLabels;
                switch (category) {
                    case CATEGORY_HAIR:
                        chipLabels = mContext.getResources()
                                .getStringArray(R.array.hair_chip_labels);
                        break;
                    case CATEGORY_BODY:
                        chipLabels = mContext.getResources()
                                .getStringArray(R.array.body_chip_labels);
                        break;
                    case CATEGORY_FACE:
                        chipLabels = mContext.getResources()
                                .getStringArray(R.array.face_chip_labels);
                        break;
                    default:
                        chipLabels = mContext.getResources()
                                .getStringArray(R.array.ingredients_chip_labels);
                }

                new ChipCloud.Configure()
                        .chipCloud(mChipCloud)
                        .selectedColor(mContext.getResources().getColor(R.color.pink200))
                        .selectedFontColor(mContext.getResources().getColor(R.color.almostWhite))
                        .deselectedColor(mContext.getResources().getColor(R.color.bluegray700))
                        .deselectedFontColor(mContext.getResources().getColor(R.color.almostWhite))
                        .selectTransitionMS(400)
                        .deselectTransitionMS(250)
                        .mode(ChipCloud.Mode.SINGLE)
                        .labels(chipLabels)
                        .allCaps(false)
                        .gravity(ChipCloud.Gravity.CENTER)
                        .textSize((int) mContext.getResources().getDimension(R.dimen.chip_text_size))
                        .minHorizontalSpacing(mContext.getResources()
                                .getDimensionPixelSize(R.dimen.chip_horiz_spacing))
                        .typeface(Typeface.createFromAsset(mContext.getAssets(),
                                "OpenSans-SemiBold.ttf"))
                        .chipListener(new ChipListener() {
                            @Override
                            public void chipSelected(int index) {
                                //...
                            }

                            @Override
                            public void chipDeselected(int index) {
                                //...
                            }
                        })
                        .build();


                mChipCloud.setSelectedChip(0);
            }

            //set appropriate order chosen
            if(viewModel.getOrder().equals(ORDER_NEW)) {
                mSwitchPopular.setTextColor(mContext.getResources().getColor(R.color.white));
                mSwitchNew.setTextColor(mContext.getResources().getColor(R.color.pink200));
            }

            mSwitchPopular.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(viewModel.getOrder().equals(ORDER_NEW)) {
                        viewModel.setOrder(ORDER_POPULAR);
                    }
                }
            });

            mSwitchNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(viewModel.getOrder().equals(ORDER_POPULAR)) {
                        viewModel.setOrder(ORDER_NEW);
                    }
                }
            });
        }
    }
}
