package com.blogspot.android_czy_java.beautytips.dependencyInjection;


import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {AndroidInjectionModule.class, ActivityBuilderModule.class})
public interface MyApplicationComponent extends AndroidInjector<MyApplication>{

}
