package com.blogspot.android_czy_java.beautytips.di.usecase.notification

import com.blogspot.android_czy_java.beautytips.di.repository.notification.NotificationRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.GetNotificationsUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [
    NotificationRepositoryModule::class
])
class NotificationUseCaseModule {

    @Provides
    fun provideGetNotificationsUseCase(notificationRepository: NotificationRepository,
                                       currentUserUseCase: GetCurrentUserUseCase) =
            GetNotificationsUseCase(notificationRepository, currentUserUseCase)

}