package com.blogspot.android_czy_java.beautytips.newTip;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.listView.firebase.NetworkConnectionHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.farbod.labelledspinner.LabelledSpinner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class NewTipActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 100;

    public static final String KEY_IMAGE_PATH = "image_path";
    public static final String KEY_CATEGORY = "category";


    @BindView(R.id.app_bar)
    Toolbar mToolbar;

    @BindView(R.id.nickname_text_view)
    TextView mNicknameTv;

    @BindView(R.id.author_photo)
    CircleImageView mAuthorPhotoIv;

    @BindView(R.id.category_spinner)
    LabelledSpinner mCategorySpinner;

    @BindView(R.id.image)
    ImageView mImageView;

    private int category;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tip);
        ButterKnife.bind(this);

        overridePendingTransition(R.anim.bottom_to_top, R.anim.fade_out);

        if(savedInstanceState != null) {
            category = savedInstanceState.getInt(KEY_CATEGORY);
            imagePath = savedInstanceState.getString(KEY_IMAGE_PATH, "");
        }

        prepareToolbar();
        prepareAuthorDesc();
        prepareSpinner();
        prepareImageView();

        Timber.d("onCreate");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_CATEGORY, category);
        if(imagePath != null) {
            outState.putString(KEY_IMAGE_PATH, imagePath);
        }
        super.onSaveInstanceState(outState);
    }

    private void prepareToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ripple_back);
        }
    }

    private void prepareAuthorDesc() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("user-photos").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String path = (String)dataSnapshot.getValue();
                        Timber.d(path);
                        Glide.with(NewTipActivity.this)
                                .load(path)
                                .into(mAuthorPhotoIv);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.e("Database error: " + databaseError.getMessage());
                    }
                });
        mNicknameTv.setText(user.getDisplayName());
    }

    private void prepareSpinner() {
        mCategorySpinner.setItemsArray(R.array.categories);
        mCategorySpinner.setSelection(category);
        mCategorySpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView,
                                     View itemView, int position, long id) {
                category = position;
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {
            }
        });
    }

    private void prepareImageView() {
        if(!TextUtils.isEmpty(imagePath)) {
            loadImageWithGlide();
        }

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RC_PHOTO_PICKER);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.top_to_bottom);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.top_to_bottom);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                imagePath = photoUri.toString();
                loadImageWithGlide();
            }
        }
    }
    private void loadImageWithGlide() {
        Glide.with(this)
                .setDefaultRequestOptions(RequestOptions.centerCropTransform())
                .load(imagePath)
                .into(mImageView);
    }

    //method to be called when "add tip" button is clicked
    public void addTip(View view) {
        if(!NetworkConnectionHelper.isInternetConnection(this)) {
            SnackbarHelper.showUnableToAddTip(view);
        } else {

        }
    }

}
