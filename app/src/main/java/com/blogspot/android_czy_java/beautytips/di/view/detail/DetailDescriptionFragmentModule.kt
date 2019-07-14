package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.detail.DetailUseCaseModule
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadBaseDetailUseCase
import com.blogspot.android_czy_java.beautytips.view.detail.DetailDescriptionFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.tablet.TabletDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    DetailDescriptionFragmentModule.ProvideViewModel::class,
    DetailUseCaseModule::class
])
abstract class DetailDescriptionFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): DetailDescriptionFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(DetailViewModel::class)
        fun provideDetailViewModel(loadBaseDetailUseCase: LoadBaseDetailUseCase): ViewModel =
                DetailViewModel(loadBaseDetailUseCase)
    }


    @Module
    class InjectViewModel {

        @Provides
        fun provideDetailViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailDescriptionFragment
        ): DetailViewModel =
                ViewModelProviders.of(target, factory).get(DetailViewModel::class.java)

        @Provides
        fun provideTabletDetailViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailDescriptionFragment
        ): TabletDetailViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(TabletDetailViewModel::class.java)
    }


}