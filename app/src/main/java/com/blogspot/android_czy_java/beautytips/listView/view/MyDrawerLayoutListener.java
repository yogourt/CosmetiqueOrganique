package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.NetworkConnectionHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.AppInfoDialog;
import com.blogspot.android_czy_java.beautytips.newTip.view.NewTipActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import timber.log.Timber;



public class MyDrawerLayoutListener implements DrawerLayout.DrawerListener {


    public static final int RC_NEW_TIP_ACTIVITY = 400;

    public static final String CATEGORY_ALL = "all";
    public static final String CATEGORY_HAIR = "hair";
    public static final String CATEGORY_FACE = "face";
    public static final String CATEGORY_BODY = "body";
    public static final String CATEGORY_YOUR_TIPS = "your_tips";
    public static final String CATEGORY_FAVOURITES = "favourites";
    public static final String CATEGORY_INGREDIENTS = "ingredients";

    public static final int NAV_POSITION_YOUR_TIPS = 0;
    public static final int NAV_POSITION_FAVOURITES = 1;
    public static final int NAV_POSITION_LOG_OUT = 3;
    public static final int NAV_POSITION_ALL = 4;
    public static final int NAV_POSITION_HAIR = 5;
    public static final int NAV_POSITION_FACE = 6;
    public static final int NAV_POSITION_BODY = 7;
    public static final int NAV_POSITION_INGREDIENTS = 8;
    public static final int NAV_POSITION_ABOUT_APP = 9;

    public static final String TAG_INFO_DIALOG = "about_this_app_dialog";

    private DrawerCreationInterface activity;
    private int itemId;

    private ListViewViewModel viewModel;


    public MyDrawerLayoutListener(final DrawerCreationInterface activity, ListViewViewModel viewModel,
                                  final int itemId) {
        this.activity = activity;
        this.itemId = itemId;
        this.viewModel = viewModel;
    }

    public interface DrawerCreationInterface {

        void logOut();

        void removeDrawerListenerFromDrawerLayout();

        void signInAnonymousUser();

        DrawerLayout getDrawerLayout();

        Context getContext();
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        Timber.d("onDrawerClosed, itemId=" + itemId);
        activity.removeDrawerListenerFromDrawerLayout();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            switch (itemId) {
                case R.id.nav_your_tips:
                    if (!user.isAnonymous()) {
                        viewModel.setNavigationPosition(NAV_POSITION_YOUR_TIPS);
                        viewModel.setCategory(CATEGORY_YOUR_TIPS);
                    } else {
                        SnackbarHelper.showFeatureForLoggedInUsersOnly(
                                activity.getContext().getResources()
                                        .getString(R.string.feature_your_tips),
                                activity.getDrawerLayout());
                    }
                    return;

                case R.id.nav_favourites:
                    if (!user.isAnonymous()) {
                        viewModel.setNavigationPosition(NAV_POSITION_FAVOURITES);
                        viewModel.setCategory(CATEGORY_FAVOURITES);
                    } else {
                        SnackbarHelper.showFeatureForLoggedInUsersOnly(activity.getContext()
                                        .getResources().getString(R.string.feature_favourites),
                                activity.getDrawerLayout());
                    }
                    return;

                case R.id.nav_add_new:
                    if (!user.isAnonymous()) {
                        if (NetworkConnectionHelper.isInternetConnection(
                                activity.getContext())) {
                            Intent intent = new Intent(activity.getContext(),
                                    NewTipActivity.class);
                            ((Activity) activity.getContext()).startActivityForResult(
                                    intent, RC_NEW_TIP_ACTIVITY);
                        } else {
                            SnackbarHelper.showUnableToAddTip(
                                    activity.getDrawerLayout());
                        }
                    } else {
                        SnackbarHelper.showFeatureForLoggedInUsersOnly(activity.getContext()
                                        .getResources().getString(R.string.feature_new_tip),
                                activity.getDrawerLayout());
                    }
                    return;

                case R.id.nav_log_out:

                    viewModel.setNavigationPosition(NAV_POSITION_ALL);
                    //it's actually logging in
                    if (user.isAnonymous()) {
                        if (NetworkConnectionHelper.isInternetConnection(
                                activity.getContext())) {
                            activity.signInAnonymousUser();
                        } else {
                            SnackbarHelper.showUnableToLogIn(
                                    activity.getDrawerLayout());
                        }
                    }
                    //this part is logging out
                    else {
                        if (NetworkConnectionHelper.isInternetConnection(
                                activity.getContext())) {
                            activity.logOut();
                        } else {
                            SnackbarHelper.showUnableToLogOut(
                                    activity.getDrawerLayout());
                        }
                    }
                    return;


                case R.id.nav_all:
                    viewModel.setNavigationPosition(NAV_POSITION_ALL);
                    viewModel.setCategory(CATEGORY_ALL);
                    return;
                case R.id.nav_hair:
                    viewModel.setNavigationPosition(NAV_POSITION_HAIR);
                    viewModel.setCategory(CATEGORY_HAIR);
                    return;
                case R.id.nav_face:
                    viewModel.setNavigationPosition(NAV_POSITION_FACE);
                    viewModel.setCategory(CATEGORY_FACE);
                    return;
                case R.id.nav_body:
                    viewModel.setNavigationPosition(NAV_POSITION_BODY);
                    viewModel.setCategory(CATEGORY_BODY);
                    return;
                case R.id.nav_ingredients:
                    viewModel.setNavigationPosition(NAV_POSITION_INGREDIENTS);
                    viewModel.setCategory(CATEGORY_INGREDIENTS);
                    return;
                case R.id.nav_about_app:
                    AppInfoDialog dialog = new AppInfoDialog();
                    dialog.show(((Activity)activity.getContext())
                            .getFragmentManager(), TAG_INFO_DIALOG);

            }

        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

}
