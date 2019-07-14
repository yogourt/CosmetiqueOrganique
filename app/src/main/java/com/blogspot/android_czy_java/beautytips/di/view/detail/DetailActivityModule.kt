package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.view.detail.DetailActivity
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.tablet.TabletDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DetailActivityModule {

    @ContributesAndroidInjector(modules = [
        DetailActivityModule.InjectViewModel::class
    ])
    abstract fun bind(): DetailActivity

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(TabletDetailViewModel::class)
        fun provideTabletDetailViewModel(): ViewModel =
                TabletDetailViewModel()
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideTabletDetailViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailActivity
        ): TabletDetailViewModel =
                ViewModelProviders.of(target, factory).get(TabletDetailViewModel::class.java)
    }
}


