package com.blogspot.android_czy_java.beautytips.listView.utils;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageHelper {

    public static void setLanguageToEnglish(Context context) {
        /*
          Here we change the language to always be English. This is done to always have FirebaseUI Auth
          log in activity in english, because strings are changed only in english version
        */
        Locale.setDefault(Locale.ENGLISH);
        Configuration config = new Configuration();
        config.locale = Locale.ENGLISH;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}


