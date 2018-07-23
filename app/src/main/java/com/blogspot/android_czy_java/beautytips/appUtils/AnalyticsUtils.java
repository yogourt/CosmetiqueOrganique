package com.blogspot.android_czy_java.beautytips.appUtils;

import android.content.Context;
import android.os.Bundle;

import com.blogspot.android_czy_java.beautytips.listView.view.MainActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsUtils {

    public static void logEventSearch(Context context, String query) {
        //add this event to firebase analytics
        Bundle analyticsBundle = new Bundle();
        analyticsBundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, query);
        FirebaseAnalytics.getInstance(context).logEvent(
                FirebaseAnalytics.Event.SEARCH, analyticsBundle);
    }
    public static void logEventTipView(Context context, String tipTitle) {
        Bundle analyticsBundle = new Bundle();
        analyticsBundle.putString("tip_title", tipTitle);
        FirebaseAnalytics.getInstance(context).logEvent("tip_opened", analyticsBundle);
    }
}
