package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseHelper;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.NetworkConnectionHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.IdpResponse;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.NAV_POSITION_LOG_OUT;

public class MainActivity extends AppCompatActivity implements ListViewAdapter.PositionListener,
        MyDrawerLayoutListener.DrawerCreationInterface, FirebaseLoginHelper.MainViewInterface,
        NicknamePickerDialog.NicknamePickerDialogListener, WelcomeDialog.WelcomeDialogListener {

    public static final String KEY_CATEGORY = "category";
    public static final String KEY_NAV_POSITION = "navigation_position";
    public static final String KEY_NAV_ITEM_ID = "navigation_item_id";
    public static final int RC_PHOTO_PICKER = 100;

    public static final String TAG_NICKNAME_DIALOG = "nickname_picker_dialog";
    public static final String TAG_WELCOME_DIALOG = "welcome_dialog";


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Inject
    FirebaseLoginHelper mLoginHelper;

    private View mHeaderLayout;
    private CircleImageView photoIv;

    private StaggeredGridLayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;
    private MyDrawerLayoutListener mDrawerListener;

    /*
      Category and navigationPosition are used in Navigation Drawer: navigationPosition is used
      to check selected item (it's rose), category is used when creating firebase query
     */
    private String category;
    private int navigationPosition;
    private int navigationItemId;

    private int listPosition;

    private boolean isPhotoSaving;

    private DialogFragment mDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
          Here we change the language to always be English. This is done to always have FirebaseUI Auth
          log in activity in english, because strings are changed only in english version
        */
        Locale.setDefault(Locale.ENGLISH);
        Configuration config = new Configuration();
        config.locale = Locale.ENGLISH;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            category = savedInstanceState.getString(KEY_CATEGORY);
            navigationPosition = savedInstanceState.getInt(KEY_NAV_POSITION,
                    MyDrawerLayoutListener.NAV_POSITION_ALL);
            navigationItemId = savedInstanceState.getInt(KEY_NAV_ITEM_ID);
        } else {
            category = MyDrawerLayoutListener.CATEGORY_ALL;
            //set navigation position to "All"
            navigationPosition = MyDrawerLayoutListener.NAV_POSITION_ALL;
        }

        Timber.d("On create");
        mLoginHelper = new FirebaseLoginHelper(this);
        getLifecycle().addObserver(mLoginHelper);

        prepareActionBar();
        prepareRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(listPosition != 0) {
            mRecyclerView.smoothScrollToPosition(listPosition);
        }
        prepareNavigationDrawer();
        mNavigationView.getMenu().getItem(navigationPosition).setChecked(true);
        mDrawerListener = new MyDrawerLayoutListener(this, navigationItemId, category);
        mDrawerLayout.addDrawerListener(mDrawerListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mDrawerListener != null) {
            mDrawerLayout.removeDrawerListener(mDrawerListener);
            mDrawerListener = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_CATEGORY, category);
        outState.putInt(KEY_NAV_POSITION, navigationPosition);
        outState.putInt(KEY_NAV_ITEM_ID, navigationItemId);

        if(mDialogFragment != null) {
            mDialogFragment.dismiss();
        }
        super.onSaveInstanceState(outState);
    }

    private void prepareActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private void prepareRecyclerView() {

        //add adapter
        mAdapter = new ListViewAdapter(this, FirebaseHelper.createFirebaseRecyclerOptions(
                category), this);
        //this is done for listening for changes in data, they will be applied automatically by adapter.
        getLifecycle().addObserver(mAdapter);

        mRecyclerView.setAdapter(mAdapter);

        int orientation = getResources().getConfiguration().orientation;
        //add layout manager
        mLayoutManager = MainActivityUtils.createLayoutManager(orientation);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //item decoration is added to make spaces between items in recycler view
        mRecyclerView.addItemDecoration(new SpacesItemDecoration((
                (int) getResources().getDimension(R.dimen.list_padding)), orientation));
    }

    /*
      here we set navigation drawer actions. There is apparently some bug in recreate(),
      because drawer is not closing but stays open on recreating, so here everything is surrounded
      by onDrawerClosed() from DrawerListener.
    */
    private void prepareNavigationDrawer() {

        MenuItem logOutItem = mNavigationView.getMenu().getItem(NAV_POSITION_LOG_OUT);

        mHeaderLayout = mNavigationView.getHeaderView(0);
        photoIv = mHeaderLayout.findViewById(R.id.nav_photo);

        if(mLoginHelper.isUserAnonymous()) {
            logOutItem.setTitle(R.string.nav_log_in);
            logOutItem.setIcon(R.drawable.ic_login);
            photoIv.setOnClickListener(null);
        } else {
            logOutItem.setTitle(R.string.nav_log_out);
            logOutItem.setIcon(R.drawable.ic_logout);

            //when user clicks photo circle the photo chooser is opening
            photoIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, RC_PHOTO_PICKER);
                }
            });
        }

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                        mDrawerLayout.closeDrawers();

                        Timber.d("on Navigation item selected");

                        /*
                        Here we need mDrawerListener to properly close NavigationDrawer while
                        changing category, which is added onResume(). Without this navigation drawer
                         will not close on recreate().
                         */
                        navigationItemId = item.getItemId();
                        mDrawerListener.setItemId(item.getItemId());

                        return true;
                    }
                }
        );
    }

    @Override
    public void onClick(int position) {
        this.listPosition = position;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //This method is called after user signed in or canceled signing in (depending on result code)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FirebaseLoginHelper.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                //signing in is handling by mLoginHelper
            } else {
                //if response is null the user canceled sign in flow using back button
                if (response == null) {
                    finish();
                }
            }
        }
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                if(!NetworkConnectionHelper.isInternetConnection(this)) {
                    SnackbarHelper.showUnableToAddImage(mRecyclerView);
                }
                else {
                    Glide.with(this)
                            .load(R.drawable.placeholder)
                            .into(photoIv);
                    SnackbarHelper.showAddImageMayTakeSomeTime(mRecyclerView);
                }
                if(isPhotoSaving) {
                    mLoginHelper.stopPreviousUserPhotoSaving();
                }
                mLoginHelper.saveUserPhoto(photoUri);
                isPhotoSaving = true;
            }
        }
    }

    public void showWelcomeDialog() {
        mDialogFragment = new WelcomeDialog();
        mDialogFragment.show(getFragmentManager(), TAG_WELCOME_DIALOG);
    }

    public  void showPickNicknameDialog() {
        mDialogFragment = new NicknamePickerDialog();
        mDialogFragment.show(getFragmentManager(), TAG_NICKNAME_DIALOG);
        getFragmentManager().executePendingTransactions();
        Window dialogWindow = mDialogFragment.getDialog().getWindow();
        if(dialogWindow != null) {
            //show soft keyboard
            dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onDialogSaveButtonClick(String nickname) {
        mLoginHelper.saveNickname(nickname);
        setNickname(nickname);
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void setNickname(String nickname) {
        TextView nicknameTv = mHeaderLayout.findViewById(R.id.nav_nickname);
        nicknameTv.setText(nickname);
    }

    public void setUserPhoto(String imageUri) {

        Glide.with(this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.color.bluegray700))
                .load(imageUri)
                .into(photoIv);
    }

    public void setNavigationPosition(int newPosition) {
        navigationPosition = newPosition;
        mNavigationView.getMenu().getItem(navigationPosition).setChecked(true);

    }

    @Override
    public void logOut() {
        mLoginHelper.logOut();
    }

    @Override
    public void signInAnonymousUser() {
        mLoginHelper.showSignInScreen();
    }

    public void setIsPhotoSaving(boolean isPhotoSaving) {
        this.isPhotoSaving = isPhotoSaving;
    }

    @Override
    public void onPositiveButtonClicked() {
        mLoginHelper.showSignInScreen();
    }

    @Override
    public void onNegativeButtonClicked() {
        mLoginHelper.signInAnonymously();
        prepareNavigationDrawer();

    }
}
