package com.blogspot.android_czy_java.beautytips.listView;


import android.support.design.widget.Snackbar;
import android.view.View;

import com.blogspot.android_czy_java.beautytips.R;

public class SnackbarHelper {

    /*
    shows snackbar with message that there is no internet connection and it's impossible to
    log out
    */
    static void showUnableToLogOut(View snackbarView) {
        snackbarWithOk(R.string.message_log_out_no_internet, snackbarView);
    }

    static void showUnableToAddTip(View snackbarView) {
        simpleSnackbar(R.string.message_add_photo_no_internet, snackbarView);
    }

    static void showUnableToAddImage(View snackbarView) {
        simpleSnackbar(R.string.message_add_photo_no_internet, snackbarView);
    }

    static void showAddImageMayTakeSomeTime(View snackbarView) {
        snackbarWithOk(R.string.message_add_photo_is_long, snackbarView);
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

    private static void simpleSnackbar(int message, View snackbarView) {
        Snackbar snackbar = Snackbar.make(snackbarView, message,
                Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(snackbarView.
                getResources().getColor(R.color.bluegray900));
        snackbar.show();
    }
}

