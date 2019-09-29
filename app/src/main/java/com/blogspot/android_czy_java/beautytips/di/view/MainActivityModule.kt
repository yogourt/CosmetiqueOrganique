package com.blogspot.android_czy_java.beautytips.di.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.account.AccountUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.view.detail.DetailActivityModule
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.view.recipe.MainActivity
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    MainActivityModule.ProvideViewModel::class,
    AccountUseCaseModule::class
])
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [
        MainActivityModule.InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): MainActivity

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(MainActivityViewModel::class)
        fun provideMainActivityViewModel(loginUseCase: LoginUseCase): ViewModel =
                MainActivityViewModel(loginUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideMainActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: MainActivity
        ): MainActivityViewModel =
                ViewModelProviders.of(target, factory).get(MainActivityViewModel::class.java)


        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: MainActivity
        ): DetailActivityViewModel =
                ViewModelProviders.of(target, factory).get(DetailActivityViewModel::class.java)

    }
}