package com.blogspot.android_czy_java.beautytips.di.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.recipe.RecipeUseCaseModule
import com.blogspot.android_czy_java.beautytips.listView.view.MainActivityFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeViewModel
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    RecipeModule.ProvideViewModel::class,
    RecipeUseCaseModule::class
])
abstract class RecipeModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class
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
        fun provideListNotesViewModel(
                factory: ViewModelProvider.Factory,
                target: MainActivityFragment
        ): RecipeViewModel =
                ViewModelProviders.of(target, factory).get(RecipeViewModel::class.java)
    }

}