package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.detail.DetailUseCaseModule
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadIngredientsUseCase
import com.blogspot.android_czy_java.beautytips.view.detail.DetailIngredientsFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.IngredientsFragmentViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    DetailIngredientsFragmentModule.ProvideViewModel::class,
    DetailUseCaseModule::class
])
abstract class DetailIngredientsFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): DetailIngredientsFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(IngredientsFragmentViewModel::class)
        fun provideImageFragmentViewModel(loadIngredientsUseCase: LoadIngredientsUseCase): ViewModel =
                IngredientsFragmentViewModel(loadIngredientsUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideIngredientsFragmentViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailIngredientsFragment
        ): IngredientsFragmentViewModel =
                ViewModelProviders.of(target, factory).get(IngredientsFragmentViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailIngredientsFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }

}