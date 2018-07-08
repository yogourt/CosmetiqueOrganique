package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.DeleteTipDialog;
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.NicknamePickerDialog;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.NetworkConnectionHelper;
import com.blogspot.android_czy_java.beautytips.listView.utils.LanguageHelper;
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.RecyclerViewHelper;
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.SpacesItemDecoration;
import com.blogspot.android_czy_java.beautytips.sync.SyncScheduleHelper;
import com.blogspot.android_czy_java.beautytips.welcome.WelcomeActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper.RC_PERMISSION_EXT_STORAGE;
import static com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel.USER_STATE_ANONYMOUS;
import static com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel.USER_STATE_NULL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.NAV_POSITION_LOG_OUT;
import static com.blogspot.android_czy_java.beautytips.newTip.view.NewTipActivity.KEY_TIP_NUMBER;
import static com.blogspot.android_czy_java.beautytips.welcome.WelcomeActivity.RESULT_LOG_IN;
import static com.blogspot.android_czy_java.beautytips.welcome.WelcomeActivity.RESULT_LOG_IN_ANONYMOUSLY;
import static com.blogspot.android_czy_java.beautytips.welcome.WelcomeActivity.RESULT_TERMINATE;

public class MainActivity extends AppCompatActivity implements ListViewAdapter.PositionListener,
        MyDrawerLayoutListener.DrawerCreationInterface, FirebaseLoginHelper.MainViewInterface,
        NicknamePickerDialog.NicknamePickerDialogListener {

    public static final int RC_PHOTO_PICKER = 100;
    public static final int RC_WELCOME_ACTIVITY = 200;
    public static final int RC_DETAIL_ACTIVITY = 300;
    public static final int RC_NEW_TIP_ACTIVITY = 400;

    public static final int RESULT_DATA_CHANGE = 10;

    public static final String TAG_NICKNAME_DIALOG = "nickname_picker_dialog";
    public static final String TAG_DELETE_TIP_DIALOG = "delete_tip_dialog";


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.search_view)
    SearchView mSearchView;

    FirebaseLoginHelper mLoginHelper;

    private View mHeaderLayout;
    private CircleImageView photoIv;
    TextView nicknameTv;

    private StaggeredGridLayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;
    private MyDrawerLayoutListener mDrawerListener;

    private boolean isPhotoSaving;

    private DialogFragment mDialogFragment;

    private ListViewViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LanguageHelper.setLanguageToEnglish(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(ListViewViewModel.class);
        viewModel.init();

        if (savedInstanceState != null) {
            Timber.d("activity is recreating");
        }
        //initialize AdMobs only once in app lifetime
        else {
            Timber.d("ad mob is initialized");
            MobileAds.initialize(this, getResources().getString(R.string.add_mob_app_id));
        }

        SyncScheduleHelper.initialize(this);


        mLoginHelper = new FirebaseLoginHelper(this, viewModel);

        prepareActionBar();
        prepareSearchView();

        //it has to be added here to avoid adding it multiple times. It doesn't have to be done on category change,
        //this is why it's not part of prepareNavigationDrawer()
        setListenerToNavigationView();

        viewModel.getCategoryLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String category) {
                Timber.d("category changed");
                prepareNavigationDrawer();
            }
        });

        viewModel.getUserStateLiveData().observe(this, createUserStateObserver());

        viewModel.getSearchLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isSearchVisible) {
                if (isSearchVisible != null) {
                    if (isSearchVisible) mSearchView.setVisibility(View.VISIBLE);
                    else mSearchView.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.getRecyclerViewLiveData().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> list) {
                prepareRecyclerView(list);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        //layout manager might not have been already initialized, as it's initialized when data is
        //fetched in FirebaseHelper and this is done in background
        if (mRecyclerView.getLayoutManager() != null) {
            viewModel.setInto(new int[2]);
            ((StaggeredGridLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstVisibleItemPositions(viewModel.getInto());
        }
    }

    @Override
    protected void onDestroy() {
        mLoginHelper = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mDialogFragment != null) {
            mDialogFragment.dismiss();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

        //the first and the last screen user sees is all tips to provide proper and predictable
        // navigation
        if (!viewModel.getCategory().equals(CATEGORY_ALL)) viewModel.setCategory(CATEGORY_ALL);
        else super.onBackPressed();
    }

    @NonNull
    private Observer<String> createUserStateObserver() {
        return new Observer<String>() {
            @Override
            public void onChanged(@Nullable String userState) {
                Timber.d("onChanged() user state observer's method, new state: " + userState);
                MenuItem logOutItem = mNavigationView.getMenu().getItem(NAV_POSITION_LOG_OUT);
                if (userState.equals(USER_STATE_NULL)) {
                    Intent welcomeActivityIntent = new Intent(MainActivity.this,
                            WelcomeActivity.class);
                    startActivityForResult(welcomeActivityIntent, RC_WELCOME_ACTIVITY);
                }
                //user just logged in anonymously
                else if (userState.equals(USER_STATE_ANONYMOUS)) {
                    logOutItem.setTitle(R.string.nav_log_in);
                    logOutItem.setIcon(R.drawable.ic_login);
                    photoIv.setOnClickListener(null);
                    photoIv.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
                    nicknameTv.setText(R.string.label_anonymous);
                }
                //user just logged in
                else {
                    logOutItem.setTitle(R.string.nav_log_out);
                    logOutItem.setIcon(R.drawable.ic_logout);
                    mLoginHelper.prepareNavDrawerHeader();

                    //when user clicks photo button_background the photo chooser is opening
                    photoIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ExternalStoragePermissionHelper
                                    .isPermissionGranted(MainActivity.this)) {
                                ExternalStoragePermissionHelper.showPhotoPicker(MainActivity.this);
                            } else {
                                ExternalStoragePermissionHelper.askForPermission(
                                        MainActivity.this);
                            }
                        }
                    });
                }
            }

        };
    }


    private void prepareActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private void prepareRecyclerView(List<ListItem> recyclerViewList) {

        //add adapter
        mAdapter = new ListViewAdapter(this, recyclerViewList, this, viewModel);

        mRecyclerView.setAdapter(mAdapter);

        int orientation = getResources().getConfiguration().orientation;
        //add layout manager
        mLayoutManager = RecyclerViewHelper.createLayoutManager(orientation);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //item decoration is added to make spaces between items in recycler view
        if (mRecyclerView.getItemDecorationCount() == 0)
            mRecyclerView.addItemDecoration(new SpacesItemDecoration((
                    (int) getResources().getDimension(R.dimen.list_padding)), orientation));
    }

    /*
      here we set navigation drawer actions. There is apparently some bug in recreate(),
      because drawer is not closing but stays open on recreating, so here everything is surrounded
      by onDrawerClosed() from DrawerListener.
    */
    private void prepareNavigationDrawer() {

        mHeaderLayout = mNavigationView.getHeaderView(0);
        photoIv = mHeaderLayout.findViewById(R.id.nav_photo);
        nicknameTv = mHeaderLayout.findViewById(R.id.nav_nickname);


        //make all items in menu unchecked and then check the one that was selected recently
        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            mNavigationView.getMenu().getItem(i).setChecked(false);
        }
        mNavigationView.getMenu().getItem(viewModel.getNavigationPosition()).setChecked(true);
    }

    private void setListenerToNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                        mDrawerLayout.closeDrawers();

                        Timber.d("on Navigation item selected");

                        /*
                        Here we need mDrawerListener to properly close NavigationDrawer when calling
                        recreate(), this is why DrawerListener is added and inside it selection of item
                        is handled.
                         */
                        mDrawerListener = new MyDrawerLayoutListener(MainActivity.this, viewModel,
                                item.getItemId());
                        mDrawerLayout.addDrawerListener(mDrawerListener);

                        return true;
                    }
                }
        );
    }

    private void prepareSearchView() {

        //on close set visibility to invisible
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                viewModel.setIsSearchVisible(false);
                //on close search view, load all the data from chosen category
                if(viewModel.getSearchWasConducted()) viewModel.notifyRecyclerDataHasChanged();
                return true;
            }
        });

        //when the user submit search, pass it to viewModel
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                viewModel.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if (item.getItemId() == R.id.menu_search) {
            if (mSearchView.getVisibility() == View.INVISIBLE) {
                mSearchView.setIconified(false);
                viewModel.setIsSearchVisible(true);
            }
            //when user clicks icon second time, the search view disappears
            else viewModel.setIsSearchVisible(false);
        }

        return super.onOptionsItemSelected(item);
    }

    //This method is called after user signed in or canceled signing in (depending on result code)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_WELCOME_ACTIVITY) {
            if (resultCode == RESULT_TERMINATE) {
                onBackPressed();
            } else if (resultCode == RESULT_LOG_IN) {
                mLoginHelper.showSignInScreen();
            } else if (resultCode == RESULT_LOG_IN_ANONYMOUSLY) {
                mLoginHelper.signInAnonymously();
            }
        }

        if (requestCode == FirebaseLoginHelper.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                mLoginHelper.signIn();
                showPickNicknameDialog();
                SyncScheduleHelper.immediateSync(this);
                //reload data to show if user likes tip
                viewModel.notifyRecyclerDataHasChanged();
            } else {
                //if response is null the user canceled sign in flow using back button, so we sign
                //him anonymously.
                if (response == null) {
                    mLoginHelper.signInAnonymously();
                }
            }
        }
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                if (!NetworkConnectionHelper.isInternetConnection(this)) {
                    SnackbarHelper.showUnableToAddImage(mRecyclerView);
                } else {
                    Glide.with(this)
                            .load(R.drawable.placeholder)
                            .into(photoIv);
                    SnackbarHelper.showAddImageMayTakeSomeTime(mRecyclerView);
                }
                if (isPhotoSaving) {
                    mLoginHelper.stopPreviousUserPhotoSaving();
                }
                mLoginHelper.saveUserPhoto(photoUri);
                isPhotoSaving = true;
            }
        }

        if (requestCode == RC_DETAIL_ACTIVITY && resultCode == RESULT_DATA_CHANGE) {
            viewModel.notifyRecyclerDataHasChanged();
        }

        if (requestCode == RC_NEW_TIP_ACTIVITY && resultCode == RESULT_DATA_CHANGE) {
            viewModel.waitForAddingImage(data.getStringExtra(KEY_TIP_NUMBER));
            viewModel.setCategory(CATEGORY_ALL);
            SnackbarHelper.showNewTipVisibleSoon(mRecyclerView);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == RC_PERMISSION_EXT_STORAGE) {
            ExternalStoragePermissionHelper.answerForPermissionResult(this, grantResults,
                    mRecyclerView);
        }
    }


   /*
       Here is the beginning of interfaces methods
     */


    /*
         Implementation of ListViewAdapter.PositionListener
     */
    @Override
    public void onClickDeleteTip(String tipId) {
        mDialogFragment = new DeleteTipDialog();
        ((DeleteTipDialog) mDialogFragment).setTipId(tipId);
        ((DeleteTipDialog) mDialogFragment).setViewModel(viewModel);
        mDialogFragment.show(getFragmentManager(), TAG_DELETE_TIP_DIALOG);
    }



    /*
       implementation of MyDrawerLayoutListener.DrawerCreationInterface
     */

    @Override
    public void logOut() {
        mLoginHelper.logOut();
    }

    @Override
    public void removeDrawerListenerFromDrawerLayout() {
        mDrawerLayout.removeDrawerListener(mDrawerListener);
    }

    //This is called by navigation drawer when clicking on "log in"
    @Override
    public void signInAnonymousUser() {
        mLoginHelper.showSignInScreen();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public Context getContext() {
        return this;
    }



    /*
      Implementation of FirebaseLoginHelper.MainViewInterface
     */

    @Override
    public void setNickname(String nickname) {
        nicknameTv.setText(nickname);
    }

    @Override
    public void setUserPhoto(String imageUri) {

        Glide.with(this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.color.bluegray700))
                .load(imageUri)
                .into(photoIv);
    }

    @Override
    public void setIsPhotoSaving(boolean isPhotoSaving) {
        this.isPhotoSaving = isPhotoSaving;
    }

    @Override
    public void showPickNicknameDialog() {
        mDialogFragment = new NicknamePickerDialog();
        mDialogFragment.show(getFragmentManager(), TAG_NICKNAME_DIALOG);
    }

    /*
       Implementation of NicknamePickerDialog.NicknamePickerDialogListener
     */
    @Override
    public void onDialogSaveButtonClick(String nickname) {
        mLoginHelper.saveNickname(nickname);
        setNickname(nickname);
    }

}
