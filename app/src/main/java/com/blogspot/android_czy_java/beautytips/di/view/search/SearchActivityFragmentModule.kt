package com.blogspot.android_czy_java.beautytips.di.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.usecase.search.CreateSearchResultRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.search.LoadSearchResultListDataUseCase
import com.blogspot.android_czy_java.beautytips.view.search.SearchActivityFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.search.SearchActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    SearchActivityFragmentModule.ProvideViewModel::class
])
abstract class SearchActivityFragmentModule {

    @ContributesAndroidInjector(modules = [
        SearchActivityFragmentModule.InjectViewModel::class
    ])
    abstract fun bind(): SearchActivityFragment

    @Module
    class InjectViewModel {

        @Provides
        fun provideSearchActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: SearchActivityFragment
        ): SearchActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(SearchActivityViewModel::class.java)

    }

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(SearchActivityViewModel::class)
        fun provideSearchActivityViewModel(createSearchResultRequestsUseCase: CreateSearchResultRequestsUseCase,
                                           loadSearchResultListDataUseCase: LoadSearchResultListDataUseCase): ViewModel =
                SearchActivityViewModel(createSearchResultRequestsUseCase, loadSearchResultListDataUseCase)
    }

}