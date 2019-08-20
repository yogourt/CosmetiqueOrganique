package com.blogspot.android_czy_java.beautytips.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blogspot.android_czy_java.beautytips.appUtils.AppViewModelFactory
import com.blogspot.android_czy_java.beautytips.di.view.detail.*
import com.blogspot.android_czy_java.beautytips.di.view.recipe.MainActivityFragmentModule
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module(includes = [
    MainActivityFragmentModule::class,
    DetailActivityModule::class,
    DetailDescriptionFragmentModule::class,
    DetailBottomActionModule::class,
    DetailIngredientsFragmentModule::class
])
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>):
            ViewModelProvider.Factory = AppViewModelFactory(providers)

}