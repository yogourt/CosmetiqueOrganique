package com.blogspot.android_czy_java.beautytips.di.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.recipe.RecipeUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.usecase.search.SearchUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.view.detail.DetailActivityModule
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
import com.blogspot.android_czy_java.beautytips.view.recipe.OneListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    SearchResultFragmentModule.ProvideViewModel::class,
    SearchUseCaseModule::class
])
abstract class SearchResultFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule.ProvideViewModel::class
    ])
    abstract fun bind(): OneListFragment.OneSearchRecipeListFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(OneListViewModel.SearchResultViewModel::class)
        fun provideSearchResultViewModel(loadListDataUseCase: LoadRecipesUseCase<SearchResultRequest>): ViewModel =
                OneListViewModel.SearchResultViewModel(loadListDataUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideSearchResultListViewModel(
                factory: ViewModelProvider.Factory,
                target: OneListFragment.OneSearchRecipeListFragment
        ): OneListViewModel.SearchResultViewModel =
                ViewModelProviders.of(target, factory).get(OneListViewModel.SearchResultViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: OneListFragment.OneSearchRecipeListFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }


}