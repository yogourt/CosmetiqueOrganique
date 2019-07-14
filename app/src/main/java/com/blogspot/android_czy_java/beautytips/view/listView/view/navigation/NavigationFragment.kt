package com.blogspot.android_czy_java.beautytips.view.listView.view.navigation

class NavigationFragment {


    /*

    public class BaseHeaderViewHolder extends RecyclerView.ViewHolder {

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

        BaseHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //if there was a search, do show in header just some text
            if (!TextUtils.isEmpty(activityCallback.getSearchQuery())) {
                mSwitchPopular.setVisibility(View.INVISIBLE);
                mSwitchNew.setVisibility(View.INVISIBLE);
                mSearchingTv.setVisibility(View.VISIBLE);
                mSearchingTv.setText(itemView.getContext().getResources().
                        getString(R.string.searching_text, activityCallback.getSearchQuery()));
                return;
            }

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


                Context context = itemView.getContext();

                new ChipCloud.Configure()
                        .chipCloud(mChipCloud)
                        .selectedColor(context.getResources().getColor(R.color.pink200))
                        .selectedFontColor(context.getResources().getColor(R.color.almostWhite))
                        .deselectedColor(context.getResources().getColor(R.color.bluegrey700_semi))
                        .deselectedFontColor(context.getResources().getColor(R.color.almostWhite))
                        .mode(ChipCloud.Mode.REQUIRED)
                        .labels(chipLabels)
                        .allCaps(false)
                        .gravity(ChipCloud.Gravity.CENTER)
                        .textSize((int) context.getResources().getDimension(R.dimen.chip_text_size))
                        .minHorizontalSpacing(context.getResources()
                                .getDimensionPixelSize(R.dimen.chip_horiz_spacing))
                        .typeface(Typeface.createFromAsset(context.getAssets(),
                                "OpenSans-SemiBold.ttf"))
                        .chipListener(new ChipListener() {
                            @Override
                            public void chipSelected(int index) {
                                activityCallback.setSubcategory(CategoryAll.valueOf(
                                        SubcategoryLabel.valueOf(chipLabels[index]).getLabel()));
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
            if (order.equals(Order.NEW)) {
                mSwitchPopular.setTextColor(context.getResources().getColor(R.color.white));
                mSwitchNew.setTextColor(context.getResources().getColor(R.color.pink200));
            }

            mSwitchPopular.setOnClickListener(view -> {
                if (viewModel.getOrder().equals(Order.NEW)) {
                    activityCallback.setOrder(Order.POPULARITY);
                }
            });

            mSwitchNew.setOnClickListener(view -> {
                if (viewModel.getOrder().equals(ORDER_POPULAR)) {
                    viewModel.setOrder(ORDER_NEW);
                }
            });
        }
    }


     */
}