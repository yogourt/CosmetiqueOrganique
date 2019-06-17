package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseHelper;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blogspot.android_czy_java.beautytips.listView.viewmodel.ListViewViewModel.ORDER_NEW;
import static com.blogspot.android_czy_java.beautytips.listView.viewmodel.ListViewViewModel.ORDER_POPULAR;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_BODY;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_FACE;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_HAIR;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;


public abstract class BaseListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int CONST_HEIGHT = 1920;
    public static final int CONST_HEIGHT_LAND = 1080;

    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";
    public static final String KEY_FAV_NUM = "fav_num";
    public static final String KEY_AUTHOR = "author";

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    public int[] itemHeightsInDp = {630, 670, 640, 600, 670, 630, 640, 600};

    Context mContext;
    int lastPosition;
    private PositionListener mPositionListener;
    List<ListItem> list;
    ListViewViewModel viewModel;

    public BaseListViewAdapter(Context context, List<ListItem> list, PositionListener
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
    public interface PositionListener {
        void onClickDeleteTip(String tipId);
    }


    @NonNull
    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType);

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
            final BaseItemViewHolder holder = (BaseItemViewHolder) h;
            final ListItem item = list.get(position - 1);
            ViewGroup.LayoutParams params = holder.mCardView.getLayoutParams();

            //here item height in pixels is calculated from height in dp
            int itemHeight;
            int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
            if (mContext.getResources().getConfiguration()
                    .orientation == Configuration.ORIENTATION_PORTRAIT) {
                itemHeight = itemHeightsInDp[(position - 1) % 8] * screenHeight / CONST_HEIGHT;
            } else {
                itemHeight = itemHeightsInDp[(position - 1) % 8] * screenHeight / CONST_HEIGHT_LAND;
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

            if (!viewModel.getCategory().equals(CATEGORY_INGREDIENTS)) {
                TipListItem tipItem = (TipListItem) item;
                //set visibility of cross
                if (tipItem.getAuthorId() != null && !FirebaseHelper.isUserNull()
                        && tipItem.getAuthorId().equals(FirebaseLoginHelper.getUserId())) {
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
                if (tipItem.inFav) holder.mHeartIcon.setVisibility(View.VISIBLE);
                else holder.mHeartIcon.setVisibility(View.INVISIBLE);
            }
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

    public void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.item_animation_fall_down);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    //this method is used when dynamic link is passed
    public abstract void openTipWithId(String id);

    public class BaseItemViewHolder extends RecyclerView.ViewHolder  {

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

        BaseItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public Bundle createSharedElementTransition() {
            Pair<View, String> imagePair = new Pair<>((View) mImage, mImage.getTransitionName());
            Pair<View, String> scrimPair = new Pair<>(mScrim, mScrim.getTransitionName());
            return ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                    imagePair, scrimPair).toBundle();
        }

    }

        public class HeaderViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.chip_cloud)
            ChipCloud mChipCloud;

            @BindView(R.id.switch_popular)
            TextView mSwitchPopular;

            @BindView(R.id.switch_new)
            TextView mSwitchNew;

            @BindView(R.id.searching_text_view)
            TextView mSearchingTv;

            @BindView(R.id.switch_layout)
            FrameLayout mSwitchLayout;

            HeaderViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

                //if there was a search, do show in header just some text
                if (viewModel.getSearchWasConducted()) {
                    mSwitchPopular.setVisibility(View.INVISIBLE);
                    mSwitchNew.setVisibility(View.INVISIBLE);
                    mSearchingTv.setVisibility(View.VISIBLE);
                    mSearchingTv.setText(mContext.getResources().
                            getString(R.string.searching_text, viewModel.getQuery()));
                    return;
                }

                String category = viewModel.getCategory();

                if (category.equals(CATEGORY_INGREDIENTS)) mSwitchLayout.setVisibility(View.GONE);
                if (category.equals(CATEGORY_HAIR) || category.equals(CATEGORY_BODY)
                        || category.equals(CATEGORY_FACE) || category.equals(CATEGORY_INGREDIENTS)) {
                    mChipCloud.setVisibility(View.VISIBLE);
                    final String[] chipLabels;
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
                            .deselectedColor(mContext.getResources().getColor(R.color.bluegray700_semi))
                            .deselectedFontColor(mContext.getResources().getColor(R.color.almostWhite))
                            .mode(ChipCloud.Mode.REQUIRED)
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
                                    viewModel.setSubcategory(chipLabels[index]);
                                }

                                @Override
                                public void chipDeselected(int index) {
                                }
                            })
                            .build();


                    //select appropriate chip
                    for (int i = 0; i < chipLabels.length; i++) {
                        if (chipLabels[i] != null &&
                                chipLabels[i].toLowerCase().equals(viewModel.getSubcategory())) {
                            mChipCloud.setSelectedChip(i);
                            break;
                        }
                    }
                }

                //set appropriate order chosen
                if (viewModel.getOrder().equals(ORDER_NEW)) {
                    mSwitchPopular.setTextColor(mContext.getResources().getColor(R.color.white));
                    mSwitchNew.setTextColor(mContext.getResources().getColor(R.color.pink200));
                }

                mSwitchPopular.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (viewModel.getOrder().equals(ORDER_NEW)) {
                            viewModel.setOrder(ORDER_POPULAR);
                        }
                    }
                });

                mSwitchNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (viewModel.getOrder().equals(ORDER_POPULAR)) {
                            viewModel.setOrder(ORDER_NEW);
                        }
                    }
                });
            }
        }
    }

