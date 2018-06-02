package com.blogspot.android_czy_java.beautytips.dependencyInjection;


import com.blogspot.android_czy_java.beautytips.listView.LoginHelper;
import com.blogspot.android_czy_java.beautytips.listView.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    LoginHelper provideLoginHelper(MainActivity activity) {
        return new LoginHelper(activity);
    }
}
