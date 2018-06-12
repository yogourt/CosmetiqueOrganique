package com.blogspot.android_czy_java.beautytips.listView.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.appUtils.SnackbarHelper;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.appUtils.NetworkConnectionHelper;
import com.blogspot.android_czy_java.beautytips.newTip.view.NewTipActivity;
import com.google.firebase.auth.FirebaseAuth;

import timber.log.Timber;

public class MainActivityUtils {

    public static final int NUM_COLUMNS_LAND = 2;
    public static final int NUM_COLUMNS_PORT = 1;

    public static final String CATEGORY_ALL = "all";
    public static final String CATEGORY_HAIR = "hair";
    public static final String CATEGORY_FACE = "face";
    public static final String CATEGORY_BODY = "body";

    public static final int NAV_POSITION_YOUR_TIPS = 0;
    public static final int NAV_POSITION_FAVOURITES = 1;
    public static final int NAV_POSITION_ALL = 4;
    public static final int NAV_POSITION_HAIR = 5;
    public static final int NAV_POSITION_FACE = 6;
    public static final int NAV_POSITION_BODY = 7;

    public static StaggeredGridLayoutManager createLayoutManager(int orientation) {

        StaggeredGridLayoutManager layoutManager;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS_LAND,
                    StaggeredGridLayoutManager.VERTICAL);
        }
        else {
            layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS_PORT,
                    StaggeredGridLayoutManager.VERTICAL);
        };

        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        return layoutManager;
    }

    public static DrawerLayout.DrawerListener createDrawerListener(final Context context,
           final DrawerCreationMethods methods, final int itemId, final String category) {
        return new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Timber.d("onDrawerClosed, itemId=" + itemId);
                switch (itemId) {

                    case R.id.nav_your_tips:
                        methods.setNavigationPosition(NAV_POSITION_YOUR_TIPS);
                        return;

                    case R.id.nav_favourites:
                        methods.setNavigationPosition(NAV_POSITION_FAVOURITES);
                        return;

                    case R.id.nav_add_new:
                        if(NetworkConnectionHelper.isInternetConnection(
                                context) && FirebaseAuth.getInstance().getCurrentUser() != null) {
                            Intent intent = new Intent(context,
                                    NewTipActivity.class);
                            methods.startActivity(intent);
                        } else {
                            SnackbarHelper.showUnableToAddTip(
                                    methods.getRecyclerView());
                         }
                        return;

                    case R.id.nav_log_out:
                        if(NetworkConnectionHelper.isInternetConnection(
                                context)) {
                            methods.getLoginHelper().logOut();
                        } else {
                            SnackbarHelper.showUnableToLogOut(
                                    methods.getRecyclerView());
                        }
                        return;

                    case R.id.nav_all:
                        if(category.equals(CATEGORY_ALL)) break;
                        methods.setCategory(CATEGORY_ALL);
                        methods.setNavigationPosition(NAV_POSITION_ALL);
                        methods.recreate();
                        return;
                    case R.id.nav_hair:
                        if(category.equals(CATEGORY_HAIR)) break;
                        methods.setCategory(CATEGORY_HAIR);
                        methods.setNavigationPosition(NAV_POSITION_HAIR);
                        methods.recreate();
                        return;
                    case R.id.nav_face:
                        if(category.equals(CATEGORY_FACE)) break;
                        methods.setCategory(CATEGORY_FACE);
                        methods.setNavigationPosition(NAV_POSITION_FACE);
                        methods.recreate();
                        return;
                    case R.id.nav_body:
                        if(category.equals(CATEGORY_BODY)) break;
                        methods.setCategory(CATEGORY_BODY);
                        methods.setNavigationPosition(NAV_POSITION_BODY);
                        methods.recreate();
                        return;

                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        };
    }

    interface DrawerCreationMethods {
        void recreate();
        void setCategory(String category);
        void setNavigationPosition(int newPosition);
        void startActivity(Intent intent);
        RecyclerView getRecyclerView();
        FirebaseLoginHelper getLoginHelper();
    }

}
