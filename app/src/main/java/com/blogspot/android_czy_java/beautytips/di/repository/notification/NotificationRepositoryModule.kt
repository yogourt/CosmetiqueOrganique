package com.blogspot.android_czy_java.beautytips.di.repository.notification

import com.blogspot.android_czy_java.beautytips.database.notification.NotificationDao
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationRepositoryModule {

    @Provides
    @Singleton
    fun provideNotificationRepository(notificationDao: NotificationDao) =
            NotificationRepository(notificationDao)
}