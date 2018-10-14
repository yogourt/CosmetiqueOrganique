package com.blogspot.android_czy_java.beautytips.appUtils;


import android.support.design.widget.Snackbar;
import android.view.View;

import com.blogspot.android_czy_java.beautytips.R;

public class SnackbarHelper {

    /*
    shows snackbar with message that there is no internet connection and it's impossible to
    log out
    */
    public static void showUnableToLogOut(View snackbarView) {
        snackbarWithOk(R.string.message_log_out_no_internet, snackbarView);
    }

    public static void showUnableToAddTip(View snackbarView) {
        snackbarWithOk(R.string.message_add_tip_no_internet, snackbarView);
    }

    public static void showUnableToAddImage(View snackbarView) {
        simpleSnackbar(R.string.message_add_photo_no_internet, snackbarView);
    }

    public static void showAddImageMayTakeSomeTime(View snackbarView) {
        snackbarWithOk(R.string.message_add_photo_is_long, snackbarView);
    }

    public static void showCannotBeEmpty(View snackbarView, String element) {
        String message = snackbarView.getResources().getString(
                R.string.message_cannot_be_empty, element);
        simpleSnackbar(message, snackbarView);
    }

    public static void showImageCannotBeEmpty(View snackbarView) {
        simpleSnackbar(R.string.message_image_cannot_be_empty, snackbarView);
    }

    public static void showAddingTipError(View snackbarView) {
        simpleSnackbar(R.string.message_add_tip_error, snackbarView);
    }

    public static void showFeatureForLoggedInUsersOnly(String feature, View snackbarView) {
        String message = snackbarView.getResources().getString(
                R.string.message_feature_for_logged_in_only, feature);
        snackbarWithOk(message, snackbarView);
    }

    public static void showUnableToLogIn(View snackbarView) {
        snackbarWithOk(R.string.message_log_in_no_internet, snackbarView);
    }

    public static void showUnableToLogInAnonymously(View snackbarView) {
        snackbarWithOk(R.string.message_log_in_anonymously_no_internet, snackbarView);
    }

    public static void showExtStoragePermissionDenied(View snackbarView) {
        snackbarWithOk(R.string.message_ext_storage_denied, snackbarView);
    }

    public static void showWaitForImageLoad(View snackbarView) {
        snackbarWithOk(R.string.message_wait_for_loading_image, snackbarView);
    }

    public static void showNewTipVisibleSoon(View snackbarView) {
        snackbarWithOk(R.string.message_new_tip_soon, snackbarView);
    }

    public static void showUnableToAddComment(View snackbarView) {
        snackbarWithOk(R.string.message_add_comment_no_internet, snackbarView);
    }

    private static void snackbarWithOk(int message, View snackbarView) {
        Snackbar snackbar = Snackbar.make(snackbarView, message,
                Snackbar.LENGTH_LONG).setAction(R.string.label_action_ok,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(snackbarView.getResources().getColor(R.color.pink200));
        snackbar.getView().setBackgroundColor(snackbarView.
                getResources().getColor(R.color.bluegray900));
        snackbar.show();
    }

    private static void snackbarWithOk(String message, View snackbarView) {
        Snackbar snackbar = Snackbar.make(snackbarView, message,
                Snackbar.LENGTH_LONG).setAction(R.string.label_action_ok,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(snackbarView.getResources().getColor(R.color.pink200));
        snackbar.getView().setBackgroundColor(snackbarView.
                getResources().getColor(R.color.bluegray900));
        snackbar.show();
    }

    private static void simpleSnackbar(int message, View snackbarView) {
        Snackbar snackbar = Snackbar.make(snackbarView, message,
                Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(snackbarView.
                getResources().getColor(R.color.bluegray900));
        snackbar.show();
    }

    private static void simpleSnackbar(String message, View snackbarView) {
        Snackbar snackbar = Snackbar.make(snackbarView, message,
                Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(snackbarView.
                getResources().getColor(R.color.bluegray900));
        snackbar.show();
    }
}

