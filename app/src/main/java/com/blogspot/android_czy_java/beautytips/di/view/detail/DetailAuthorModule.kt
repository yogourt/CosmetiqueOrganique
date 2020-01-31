package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.usecase.detail.*
import com.blogspot.android_czy_java.beautytips.view.detail.DetailAuthorFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.AuthorViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    DetailAuthorModule.ProvideViewModel::class
])
abstract class DetailAuthorModule {

    @ContributesAndroidInjector(modules = [
        DetailAuthorModule.InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): DetailAuthorFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(AuthorViewModel::class)
        fun provideAuthorViewModel(getRecipeAuthorUseCase: GetRecipeAuthorUseCase): ViewModel =
                AuthorViewModel(getRecipeAuthorUseCase)

    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailAuthorFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)

        @Provides
        fun provideAuthorViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailAuthorFragment
        ): AuthorViewModel =
                ViewModelProviders.of(target, factory).get(AuthorViewModel::class.java)


    }

}