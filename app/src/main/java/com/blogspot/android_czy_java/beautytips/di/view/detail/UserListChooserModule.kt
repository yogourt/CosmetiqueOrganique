package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.view.account.UserListFragmentModule
import com.blogspot.android_czy_java.beautytips.view.detail.DetailActivity
import com.blogspot.android_czy_java.beautytips.view.detail.UserListChooserDialogFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.UserListViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    UserListFragmentModule.ProvideViewModel::class
])
abstract class UserListChooserModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class
    ])
    abstract fun bind(): UserListChooserDialogFragment

    @Module
    class InjectViewModel {

        @Provides
        fun provideUserListViewModel(
                factory: ViewModelProvider.Factory,
                target: UserListChooserDialogFragment
        ): UserListViewModel =
                ViewModelProviders.of(target, factory).get(UserListViewModel::class.java)

        @Provides
        fun provideHeaderViewModel(
                factory: ViewModelProvider.Factory,
                target: UserListChooserDialogFragment
        ): HeaderViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(HeaderViewModel::class.java)

    }
}