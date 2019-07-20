package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.detail.DetailUseCaseModule
import com.blogspot.android_czy_java.beautytips.view.detail.DetailHeaderFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderFragmentViewModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadHeaderFragmentDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    HeaderFragmentModule.ProvideViewModel::class,
    DetailUseCaseModule::class
])
abstract class HeaderFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): DetailHeaderFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(HeaderFragmentViewModel::class)
        fun provideImageFragmentViewModel(loadHeaderFragmentDataUseCase: LoadHeaderFragmentDataUseCase): ViewModel =
                HeaderFragmentViewModel(loadHeaderFragmentDataUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideImageFragmentViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailHeaderFragment
        ): HeaderFragmentViewModel =
                ViewModelProviders.of(target, factory).get(HeaderFragmentViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailHeaderFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }


}

