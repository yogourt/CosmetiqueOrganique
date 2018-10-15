
package com.blogspot.android_czy_java.beautytips.ingredient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.BaseItemActivity;
import com.blogspot.android_czy_java.beautytips.listView.view.MainActivity;

import butterknife.ButterKnife;

import static com.blogspot.android_czy_java.beautytips.listView.view.BaseMainActivity.KEY_QUERY;

public class IngredientActivity extends BaseItemActivity
        implements IngredientActivityFragment.IngredientFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        if(getIntent().getAction() != null)
        overridePendingTransition(R.anim.bottom_to_top, R.anim.fade_out);
        ButterKnife.bind(this);
    }

    @Override
    public void search(String query) {
        Intent listViewIntent = new Intent(this, MainActivity.class);
        listViewIntent.setAction(Intent.ACTION_SEARCH);
        listViewIntent.putExtra(KEY_QUERY, query);
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
