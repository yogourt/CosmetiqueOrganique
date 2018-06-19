package com.blogspot.android_czy_java.beautytips.listView.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.NetworkConnectionHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.newTip.view.NewTipActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import timber.log.Timber;


public class MyDrawerLayoutListener implements DrawerLayout.DrawerListener {

    public static final String CATEGORY_ALL = "all";
    public static final String CATEGORY_HAIR = "hair";
    public static final String CATEGORY_FACE = "face";
    public static final String CATEGORY_BODY = "body";
    public static final String CATEGORY_YOUR_TIPS = "your_tips";
    public static final String CATEGORY_FAVOURITES = "favourites";

    public static final int NAV_POSITION_YOUR_TIPS = 0;
    public static final int NAV_POSITION_FAVOURITES = 1;
    public static final int NAV_POSITION_LOG_OUT = 3;
    public static final int NAV_POSITION_ALL = 4;
    public static final int NAV_POSITION_HAIR = 5;
    public static final int NAV_POSITION_FACE = 6;
    public static final int NAV_POSITION_BODY = 7;
    
    private DrawerCreationInterface activity;
    private int itemId;
    private String category;


    public MyDrawerLayoutListener(final DrawerCreationInterface activity, 
                                  final int itemId, final String category) {
        this.activity = activity;
        this.itemId = itemId;
        this.category = category;
    }
    
    interface DrawerCreationInterface {
        void recreate();
        void setCategory(String category);
        void setNavigationPosition(int newPosition);
        void startActivity(Intent intent);
        void logOut();
        void removeDrawerListenerFromDrawerLayout();
        void signInAnonymousUser();
        RecyclerView getRecyclerView();
        Resources getResources();
        Context getContext();
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

            if(user != null) {

                switch (itemId) {
                case R.id.nav_your_tips:
                    if (!user.isAnonymous()) {
                        if (category.equals(CATEGORY_YOUR_TIPS)) break;
                        activity.setCategory(CATEGORY_YOUR_TIPS);
                        activity.setNavigationPosition(NAV_POSITION_YOUR_TIPS);
                        activity.recreate();
                    } else {
                        SnackbarHelper.showFeatureForLoggedInUsersOnly(activity.getResources()
                                        .getString(R.string.feature_your_tips),
                                activity.getRecyclerView());
                    }
                    return;

                case R.id.nav_favourites:
                    if (!user.isAnonymous()) {
                        if (category.equals(CATEGORY_FAVOURITES)) break;
                        activity.setCategory(CATEGORY_FAVOURITES);
                        activity.setNavigationPosition(NAV_POSITION_FAVOURITES);
                        activity.recreate();
                    } else {
                        SnackbarHelper.showFeatureForLoggedInUsersOnly(activity.getResources()
                                .getString(R.string.feature_favourites), activity.getRecyclerView());
                    }
                    return;

                case R.id.nav_add_new:
                    if(!user.isAnonymous()) {
                        if (NetworkConnectionHelper.isInternetConnection(
                                activity.getContext())) {
                                Intent intent = new Intent(activity.getContext(),
                                        NewTipActivity.class);
                                activity.startActivity(intent);
                        } else {
                            SnackbarHelper.showUnableToAddTip(
                                    activity.getRecyclerView());
                        }
                    } else {
                        SnackbarHelper.showFeatureForLoggedInUsersOnly(activity.getResources()
                                        .getString(R.string.feature_new_tip),
                                activity.getRecyclerView());
                    }
                    return;

                case R.id.nav_log_out:

                    activity.setCategory(CATEGORY_ALL);
                    activity.setNavigationPosition(NAV_POSITION_ALL);
                    itemId = R.id.nav_all;
                    //it's actually logging in
                    if(user.isAnonymous()) {
                        if(NetworkConnectionHelper.isInternetConnection(
                                activity.getContext())) {
                            activity.signInAnonymousUser();
                        } else {
                            SnackbarHelper.showUnableToLogIn(
                                    activity.getRecyclerView());
                        }
                    }
                    //this part is logging out
                    else {
                        if (NetworkConnectionHelper.isInternetConnection(
                                activity.getContext())) {
                            activity.logOut();
                        } else {
                            SnackbarHelper.showUnableToLogOut(
                                    activity.getRecyclerView());
                        }
                    }
                        return;


                case R.id.nav_all:
                    if (category.equals(CATEGORY_ALL)) break;
                    activity.setCategory(CATEGORY_ALL);
                    activity.setNavigationPosition(NAV_POSITION_ALL);
                    activity.recreate();
                    return;
                case R.id.nav_hair:
                    if (category.equals(CATEGORY_HAIR)) break;
                    activity.setCategory(CATEGORY_HAIR);
                    activity.setNavigationPosition(NAV_POSITION_HAIR);
                    activity.recreate();
                    return;
                case R.id.nav_face:
                    if (category.equals(CATEGORY_FACE)) break;
                    activity.setCategory(CATEGORY_FACE);
                    activity.setNavigationPosition(NAV_POSITION_FACE);
                    activity.recreate();
                    return;
                case R.id.nav_body:
                    if (category.equals(CATEGORY_BODY)) break;
                    activity.setCategory(CATEGORY_BODY);
                    activity.setNavigationPosition(NAV_POSITION_BODY);
                    activity.recreate();
                    return;

            }

        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }
    
}
