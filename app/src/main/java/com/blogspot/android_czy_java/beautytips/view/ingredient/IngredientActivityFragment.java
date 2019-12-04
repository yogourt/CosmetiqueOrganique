package com.blogspot.android_czy_java.beautytips.view.ingredient;


import androidx.fragment.app.Fragment;


public class IngredientActivityFragment extends Fragment {

    /*
    @BindView(R.firebaseId.properties_layout)
    LinearLayout mPropertiesLayout;

    @BindView(R.firebaseId.searching_text_view)
    TextView mSearchTv;

    private DetailActivityViewModel viewModel;

    private IngredientFragmentInterface activity;


    public IngredientActivityFragment() {
        // Required empty public constructor
    }
*/
    public interface IngredientFragmentInterface {
        void search(String query);
    }

    /*

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(DetailActivityViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient_activity, container,
                false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activity = (IngredientFragmentInterface) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);

        if (!isTablet || isPortrait) {
            if (getActivity() != null) {
                item = (ListItem) getActivity().getIntent().getExtras().getSerializable(KEY_ITEM);
            }
        } else {
            //item = activityViewModel.getChosenIngredient();

        }

        if(item != null) prepareContent();
    }

    private void prepareContent() {

        //prepare search for label
        mSearchTv.setText(getResources().getString(R.string.search_for_label, item.getTitle()));
        mSearchTv.setPaintFlags(mSearchTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFor();
            }
        });

        FirebaseDatabase.getInstance().getReference("ingredients/" + item.getFirebaseId()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mPropertiesLayout.removeAllViews();

                        for (DataSnapshot propertySnapshot : dataSnapshot.getChildren()) {
                            String description = String.valueOf(propertySnapshot.getValue());
                            String key = propertySnapshot.getKey();

                            String title = "";
                            if (key != null) {
                                switch (key) {
                                    case "1":
                                        title = "Overview:";
                                        break;
                                    case "2":
                                        title = "For hair:";
                                        break;
                                    case "3":
                                        title = "For face:";
                                        break;
                                    case "4":
                                        title = "For body:";
                                        break;
                                }
                            }

                            View propertyView = LayoutInflater.from(IngredientActivityFragment.
                                    this.getContext()).
                                    inflate(R.layout.layout_ingredient_properties,
                                            mPropertiesLayout, false);
                            ((TextView) propertyView.findViewById(R.firebaseId.title_text_view)).
                                    setText(title);
                            ((TextView) propertyView.findViewById(R.firebaseId.desc_text_view)).
                                    setText(description);
                            mPropertiesLayout.addView(propertyView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    public void searchFor() {
        activity.search(item.title);
    }

    */

}
