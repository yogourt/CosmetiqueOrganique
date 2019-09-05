package com.blogspot.android_czy_java.beautytips.di.view.account

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.view.account.ProfileDetailsFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileDetailsFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        AccountActivityFragmentModule.ProvideViewModel::class
    ])
    abstract fun bind(): ProfileDetailsFragment

    @Module
    class InjectViewModel {

        @Provides
        fun provideAccountViewModel(
                factory: ViewModelProvider.Factory,
                target: ProfileDetailsFragment
        ): AccountViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(AccountViewModel::class.java)
    }

}