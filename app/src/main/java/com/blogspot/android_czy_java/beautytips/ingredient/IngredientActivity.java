package com.blogspot.android_czy_java.beautytips.ingredient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.view.BaseItemActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends BaseItemActivity {

    @BindView(R.id.properties_layout)
    LinearLayout mPropertiesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);

        prepareContent();
    }

    private void prepareContent() {
        FirebaseDatabase.getInstance().getReference("ingredients/" + mId).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot propertySnapshot: dataSnapshot.getChildren()) {
                            String description = String.valueOf(propertySnapshot.getValue());
                            String title = propertySnapshot.getKey();

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
}
