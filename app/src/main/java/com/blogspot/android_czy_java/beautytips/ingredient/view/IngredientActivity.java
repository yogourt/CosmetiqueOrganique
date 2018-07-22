package com.blogspot.android_czy_java.beautytips.ingredient.view;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.view.BaseItemActivity;
import com.blogspot.android_czy_java.beautytips.listView.view.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.haha.perflib.Main;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class IngredientActivity extends BaseItemActivity {

    public static final String KEY_QUERY = "query";

    @BindView(R.id.properties_layout)
    LinearLayout mPropertiesLayout;

    @BindView(R.id.searching_text_view)
    TextView mSearchTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        if(getIntent().getAction() != null)
        overridePendingTransition(R.anim.bottom_to_top, R.anim.fade_out);
        ButterKnife.bind(this);

        prepareContent();
    }

    private void prepareContent() {

        //prepare search for label
        mSearchTv.setText(getResources().getString(R.string.search_for_label, mTitle));
        mSearchTv.setPaintFlags(mSearchTv.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        FirebaseDatabase.getInstance().getReference("ingredients/" + mId).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot propertySnapshot: dataSnapshot.getChildren()) {
                            String description = String.valueOf(propertySnapshot.getValue());
                            String key = propertySnapshot.getKey();

                            String title = "";
                            if(key != null) {
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

                            View propertyView = LayoutInflater.from(IngredientActivity.this).
                                    inflate(R.layout.layout_ingredient_properties,
                                            mPropertiesLayout, false);
                            ((TextView)propertyView.findViewById(R.id.title_text_view)).
                                    setText(title);
                            ((TextView)propertyView.findViewById(R.id.desc_text_view)).
                                    setText(description);
                            mPropertiesLayout.addView(propertyView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    public void searchFor(View view) {
        Intent listViewIntent = new Intent(this, MainActivity.class);
        listViewIntent.setAction(Intent.ACTION_SEARCH);
        listViewIntent.putExtra(KEY_QUERY, mTitle);
        startActivity(listViewIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getAction() != null) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.top_to_bottom);
        }

    }
}
