package com.blogspot.android_czy_java.beautytips.di.view.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.recipe.RecipeUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.view.detail.DetailActivityModule
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.RecipeRequest
import com.blogspot.android_czy_java.beautytips.view.recipe.OneListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    OneRecipeListFragmentModule.ProvideViewModel::class,
    RecipeUseCaseModule::class
])
abstract class OneRecipeListFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule.ProvideViewModel::class
    ])
    abstract fun bind(): OneListFragment.OneRecipeListFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(OneListViewModel.OneRecipeListViewModel::class)
        fun provideOneListViewModel(loadListDataUseCase: LoadRecipesUseCase<RecipeRequest>): ViewModel =
                OneListViewModel.OneRecipeListViewModel(loadListDataUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideOneListViewModel(
                factory: ViewModelProvider.Factory,
                target: OneListFragment.OneRecipeListFragment
        ): OneListViewModel.OneRecipeListViewModel =
                ViewModelProviders.of(target, factory).get(OneListViewModel.OneRecipeListViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: OneListFragment.OneRecipeListFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }


}