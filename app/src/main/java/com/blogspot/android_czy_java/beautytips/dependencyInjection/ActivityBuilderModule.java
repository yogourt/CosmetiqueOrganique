package com.blogspot.android_czy_java.beautytips.dependencyInjection;

import com.blogspot.android_czy_java.beautytips.listView.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity contributeMainActivityInjector();

}
