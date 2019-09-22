package com.blogspot.android_czy_java.beautytips.di.view.search

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.view.detail.DetailActivityModule
import com.blogspot.android_czy_java.beautytips.view.search.SearchResultFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.search.SearchActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchResultFragmentModule {

    @ContributesAndroidInjector(modules = [
        SearchResultFragmentModule.InjectViewModel::class,
        SearchActivityFragmentModule::class,
        DetailActivityModule.ProvideViewModel::class
    ])
    abstract fun bind(): SearchResultFragment

    @Module
    class InjectViewModel {

        @Provides
        fun provideSearchActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: SearchResultFragment
        ): SearchActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(SearchActivityViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: SearchResultFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)

    }


}