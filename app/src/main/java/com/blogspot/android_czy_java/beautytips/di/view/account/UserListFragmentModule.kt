package com.blogspot.android_czy_java.beautytips.di.view.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.account.AccountUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.view.detail.DetailActivityModule
import com.blogspot.android_czy_java.beautytips.usecase.account.CreateUserListRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.LoadRecipesFromUserListUseCase
import com.blogspot.android_czy_java.beautytips.view.account.AccountActivityFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.UserListViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    UserListFragmentModule.ProvideViewModel::class,
    DetailActivityModule.ProvideViewModel::class,
    AccountUseCaseModule::class
])
abstract class UserListFragmentModule {

    @ContributesAndroidInjector(modules = [
        UserListFragmentModule.InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): AccountActivityFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(UserListViewModel::class)
        fun provideUserListViewModel(createUserListRequestsUseCase: CreateUserListRequestsUseCase,
                                     loadRecipesFromUserListUseCase: LoadRecipesFromUserListUseCase): ViewModel =
                UserListViewModel(createUserListRequestsUseCase, loadRecipesFromUserListUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideUserListViewModel(
                factory: ViewModelProvider.Factory,
                target: AccountActivityFragment
        ): UserListViewModel =
                ViewModelProviders.of(target, factory).get(UserListViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: AccountActivityFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }


}