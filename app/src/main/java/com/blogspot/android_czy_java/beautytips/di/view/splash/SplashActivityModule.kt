package com.blogspot.android_czy_java.beautytips.di.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.livedata.LiveDataModule
import com.blogspot.android_czy_java.beautytips.di.usecase.account.AccountUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.usecase.splash.SplashUseCaseModule
import com.blogspot.android_czy_java.beautytips.livedata.common.NetworkLiveData
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.CreateUserListRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.LoadRecipesFromUserListUseCase
import com.blogspot.android_czy_java.beautytips.usecase.splash.FetchDataFromFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.view.account.UserListFragment
import com.blogspot.android_czy_java.beautytips.view.splash.SplashActivity
import com.blogspot.android_czy_java.beautytips.viewmodel.account.UserListViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.splash.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    AccountUseCaseModule::class,
    LiveDataModule::class,
    SplashUseCaseModule::class,
    SplashActivityModule.ProvideViewModel::class
])
abstract class SplashActivityModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class
    ])
    abstract fun inject(): SplashActivity

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(SplashViewModel::class)
        fun provideSplashViewModel(fetchDataFromFirebaseUseCase: FetchDataFromFirebaseUseCase,
                                   loginUseCase: LoginUseCase,
                                   networkLiveData: NetworkLiveData): ViewModel =
                SplashViewModel(fetchDataFromFirebaseUseCase, loginUseCase, networkLiveData)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideSplashViewModel(
                factory: ViewModelProvider.Factory,
                target: SplashActivity
        ): SplashViewModel =
                ViewModelProviders.of(target, factory).get(SplashViewModel::class.java)
    }
}