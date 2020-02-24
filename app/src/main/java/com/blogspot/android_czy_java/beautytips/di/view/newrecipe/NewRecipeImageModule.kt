package com.blogspot.android_czy_java.beautytips.di.view.newrecipe

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.view.newrecipe.NewRecipeImageFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.newrecipe.NewRecipeActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    NewRecipeActivityModule.ProvideViewModel::class
])
abstract class NewRecipeImageModule {

    @ContributesAndroidInjector(modules = [
        NewRecipeImageModule.InjectViewModel::class
    ])
    abstract fun bind(): NewRecipeImageFragment


    @Module
    class InjectViewModel {

        @Provides
        fun provideNewRecipeActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: NewRecipeImageFragment
        ): NewRecipeActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(NewRecipeActivityViewModel::class.java)
    }

}