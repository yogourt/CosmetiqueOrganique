package com.blogspot.android_czy_java.beautytips.di.view.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.recipe.RecipeUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.view.detail.DetailActivityModule
import com.blogspot.android_czy_java.beautytips.view.listView.view.MainActivityFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeViewModel
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    MainActivityFragmentModule.ProvideViewModel::class,
    DetailActivityModule.ProvideViewModel::class,
    RecipeUseCaseModule::class
])
abstract class MainActivityFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): MainActivityFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(RecipeViewModel::class)
        fun provideRecipeViewModel(loadRecipesUseCase: LoadRecipesUseCase): ViewModel =
                RecipeViewModel(loadRecipesUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideRecipeViewModel(
                factory: ViewModelProvider.Factory,
                target: MainActivityFragment
        ): RecipeViewModel =
                ViewModelProviders.of(target, factory).get(RecipeViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: MainActivityFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }


}