package com.blogspot.android_czy_java.beautytips.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blogspot.android_czy_java.beautytips.appUtils.AppViewModelFactory
import com.blogspot.android_czy_java.beautytips.di.detail.ImageModule
import com.blogspot.android_czy_java.beautytips.di.recipe.RecipeModule
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module(includes = [
    RecipeModule::class,
    ImageModule::class
])
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>):
            ViewModelProvider.Factory = AppViewModelFactory(providers)

}