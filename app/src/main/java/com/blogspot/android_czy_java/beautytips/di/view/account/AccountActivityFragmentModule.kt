package com.blogspot.android_czy_java.beautytips.di.view.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.livedata.LiveDataModule
import com.blogspot.android_czy_java.beautytips.di.usecase.account.AccountUseCaseModule
import com.blogspot.android_czy_java.beautytips.livedata.common.NetworkLiveData
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.view.account.AccountActivityFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    AccountActivityFragmentModule.ProvideViewModel::class,
    AccountUseCaseModule::class,
    LiveDataModule::class
])
abstract class AccountActivityFragmentModule {

    @ContributesAndroidInjector(modules = [
        AccountActivityFragmentModule.InjectViewModel::class
    ])
    abstract fun bind(): AccountActivityFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(AccountViewModel::class)
        fun provideAccountViewModel(loginUseCase: LoginUseCase,
                                    getCurrentUserUseCase: GetCurrentUserUseCase,
                                    networkLiveData: NetworkLiveData): ViewModel =
                AccountViewModel(loginUseCase, getCurrentUserUseCase, networkLiveData)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideAccountViewModel(
                factory: ViewModelProvider.Factory,
                target: AccountActivityFragment
        ): AccountViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(AccountViewModel::class.java)


    }
}