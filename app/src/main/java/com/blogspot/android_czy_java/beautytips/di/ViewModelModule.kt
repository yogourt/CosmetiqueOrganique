package com.blogspot.android_czy_java.beautytips.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blogspot.android_czy_java.beautytips.appUtils.AppViewModelFactory
import com.blogspot.android_czy_java.beautytips.di.view.MainActivityModule
import com.blogspot.android_czy_java.beautytips.di.view.account.AccountActivityFragmentModule
import com.blogspot.android_czy_java.beautytips.di.view.account.ProfileDetailsFragmentModule
import com.blogspot.android_czy_java.beautytips.di.view.account.UserListFragmentModule
import com.blogspot.android_czy_java.beautytips.di.view.detail.*
import com.blogspot.android_czy_java.beautytips.di.view.recipe.MainActivityFragmentModule
import com.blogspot.android_czy_java.beautytips.di.view.search.SearchActivityFragmentModule
import com.blogspot.android_czy_java.beautytips.di.view.search.SearchFragmentModule
import com.blogspot.android_czy_java.beautytips.di.view.search.SearchResultFragmentModule
import com.blogspot.android_czy_java.beautytips.di.view.splash.SplashActivityModule
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module(includes = [
    MainActivityModule::class,
    MainActivityFragmentModule::class,
    DetailActivityModule::class,
    DetailDescriptionFragmentModule::class,
    DetailBottomActionModule::class,
    DetailIngredientsFragmentModule::class,
    UserListFragmentModule::class,
    AccountActivityFragmentModule::class,
    ProfileDetailsFragmentModule::class,
    SplashActivityModule::class,
    SearchActivityFragmentModule::class,
    SearchFragmentModule::class,
    SearchResultFragmentModule::class
])
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>):
            ViewModelProvider.Factory = AppViewModelFactory(providers)

}