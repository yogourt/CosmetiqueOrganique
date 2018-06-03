package com.blogspot.android_czy_java.beautytips.listView;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.blogspot.android_czy_java.beautytips.R;

class NetworkConnectionHelper {

    //checks if there is internet connection
    static boolean isInternetConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    static void showUnableToLogOut(View snackbarView) {

        Snackbar snackbar = Snackbar.make(snackbarView, R.string.message_log_out_no_internet,
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
}
