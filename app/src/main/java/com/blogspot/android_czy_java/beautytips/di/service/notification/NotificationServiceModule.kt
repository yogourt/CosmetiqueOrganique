package com.blogspot.android_czy_java.beautytips.di.service.notification

import com.blogspot.android_czy_java.beautytips.service.notification.NotificationService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NotificationServiceModule {

    @ContributesAndroidInjector
    abstract fun bind(): NotificationService
}