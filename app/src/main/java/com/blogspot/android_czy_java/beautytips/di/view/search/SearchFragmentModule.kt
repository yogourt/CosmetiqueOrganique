package com.blogspot.android_czy_java.beautytips.di.view.search

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.view.search.SearchFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.search.SearchActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchFragmentModule {

    @ContributesAndroidInjector(modules = [
        SearchFragmentModule.InjectViewModel::class,
        SearchActivityFragmentModule::class
    ])
    abstract fun bind(): SearchFragment

    @Module
    class InjectViewModel {

        @Provides
        fun provideSearchActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: SearchFragment
        ): SearchActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(SearchActivityViewModel::class.java)

    }

}