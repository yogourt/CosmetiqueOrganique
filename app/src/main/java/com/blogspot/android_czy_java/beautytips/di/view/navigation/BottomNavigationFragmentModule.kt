package com.blogspot.android_czy_java.beautytips.di.view.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.notification.NotificationUseCaseModule
import com.blogspot.android_czy_java.beautytips.usecase.comment.GetCommentNumberUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.GetNotificationNumberUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.GetNotificationsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.MakeNotificationSeenUseCase
import com.blogspot.android_czy_java.beautytips.view.common.BottomNavigationFragment
import com.blogspot.android_czy_java.beautytips.view.notification.NotificationFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.common.NavigationViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    BottomNavigationFragmentModule.ProvideViewModel::class,
    NotificationUseCaseModule::class
])
abstract class BottomNavigationFragmentModule {

    @ContributesAndroidInjector(modules = [
        BottomNavigationFragmentModule.InjectViewModel::class])
    abstract fun bind(): BottomNavigationFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(NavigationViewModel::class)
        fun provideNavigationViewModel(getNotificationNumberUseCase: GetNotificationNumberUseCase): ViewModel =
                NavigationViewModel(getNotificationNumberUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideNavigationViewModel(
                factory: ViewModelProvider.Factory,
                target: BottomNavigationFragment
        ): NavigationViewModel =
                ViewModelProviders.of(target, factory).get(NavigationViewModel::class.java)
    }

}