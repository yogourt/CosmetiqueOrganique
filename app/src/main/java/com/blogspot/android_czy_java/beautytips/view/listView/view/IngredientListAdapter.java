package com.blogspot.android_czy_java.beautytips.view.listView.view;

/*
public class IngredientListAdapter extends BaseListViewAdapter<IngredientModel> {

    public static final int REQUEST_CODE_DETAIL_ACTIVITY = 50;


    public IngredientListAdapter(Context context,
                                 List<IngredientModel> list,
                                 PositionListener positionListener,
                                 ListViewViewModel activityViewModel,
                                 DetailActivityViewModel detailActivityViewModel,
                                 float itemHeightDivider) {

        super(context, list, positionListener, activityViewModel, detailActivityViewModel);

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


    public class ItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {


        ItemViewHolder(View itemView) {
            super(itemView);
            mImage.setOnClickListener(this);
            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (activityViewModel.getCategory().equals(CATEGORY_INGREDIENTS)) {

                IngredientModel item = list.get(getAdapterPosition() - 1);

                //if the configuration is portrait, start detail activity
                if (mContext.getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT) {

                    Bundle bundle = new Bundle();
                    bundle.putLong(KEY_ID, item.getIngredientId());
                    Intent ingredientActivityIntent = new Intent(mContext, IngredientActivity.class);
                    ingredientActivityIntent.putExtras(bundle);
                    mContext.startActivity(ingredientActivityIntent, createSharedElementTransition());
                }

                detailActivityViewModel.onIngredientClick(item.getId());

            }
        }
    }

}
*/
