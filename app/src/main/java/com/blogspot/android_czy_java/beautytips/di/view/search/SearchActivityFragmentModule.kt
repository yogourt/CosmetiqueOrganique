package com.blogspot.android_czy_java.beautytips.di.view.search

import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.usecase.search.CreateSearchResultRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.search.LoadSearchResultListDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.search.SearchActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [
    SearchActivityFragmentModule.ProvideViewModel::class
])
class SearchActivityFragmentModule {

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