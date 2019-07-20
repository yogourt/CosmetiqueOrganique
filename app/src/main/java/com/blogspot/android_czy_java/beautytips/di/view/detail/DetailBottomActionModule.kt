package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.view.detail.DetailBottomActionFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailBottomActionModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): DetailBottomActionFragment

    @Module
    class InjectViewModel {

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailBottomActionFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }

}