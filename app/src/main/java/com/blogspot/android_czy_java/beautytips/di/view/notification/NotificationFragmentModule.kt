package com.blogspot.android_czy_java.beautytips.di.view.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.notification.NotificationUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.view.account.AccountActivityFragmentModule
import com.blogspot.android_czy_java.beautytips.di.view.detail.DetailActivityModule
import com.blogspot.android_czy_java.beautytips.usecase.notification.GetNotificationsUseCase
import com.blogspot.android_czy_java.beautytips.view.notification.NotificationFragment
import com.blogspot.android_czy_java.beautytips.view.recipe.MainActivityFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.notification.NotificationViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    NotificationFragmentModule.ProvideViewModel::class,
    NotificationUseCaseModule::class
])
abstract class NotificationFragmentModule {

    @ContributesAndroidInjector(modules = [
        NotificationFragmentModule.InjectViewModel::class,
        AccountActivityFragmentModule.ProvideViewModel::class,
        DetailActivityModule.ProvideViewModel::class
    ])
    abstract fun bind(): NotificationFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(NotificationViewModel::class)
        fun provideNotificationViewModel(getNotificationsUseCase: GetNotificationsUseCase): ViewModel =
                NotificationViewModel(getNotificationsUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideNotificationViewModel(
                factory: ViewModelProvider.Factory,
                target: NotificationFragment
        ): NotificationViewModel =
                ViewModelProviders.of(target, factory).get(NotificationViewModel::class.java)

        @Provides
        fun provideAccountViewModel(
                factory: ViewModelProvider.Factory,
                target: NotificationFragment
        ): AccountViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(AccountViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: NotificationFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }

}