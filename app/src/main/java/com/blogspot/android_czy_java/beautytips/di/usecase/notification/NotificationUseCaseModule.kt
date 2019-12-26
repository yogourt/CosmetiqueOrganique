package com.blogspot.android_czy_java.beautytips.di.usecase.notification

import com.blogspot.android_czy_java.beautytips.di.repository.notification.NotificationRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.GetUserFromFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.GetNotificationsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.ProcessNotificationUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.SaveNotificationUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [
    NotificationRepositoryModule::class
])
class NotificationUseCaseModule {

    @Provides
    fun provideGetNotificationsUseCase(notificationRepository: NotificationRepository,
                                       currentUserUseCase: GetCurrentUserUseCase,
                                       getUserFromFirebaseUseCase: GetUserFromFirebaseUseCase) =
            GetNotificationsUseCase(notificationRepository, currentUserUseCase, getUserFromFirebaseUseCase)

    @Provides
    fun provideSaveNotificationUseCase(notificationRepository: NotificationRepository,
                                       recipeDetailRepository: RecipeDetailRepository,
                                       commentRepository: CommentRepository) =
            SaveNotificationUseCase(notificationRepository, recipeDetailRepository, commentRepository)

    @Provides
    fun provideProcessNotificationUseCase(getUserFromFirebaseUseCase: GetUserFromFirebaseUseCase) =
            ProcessNotificationUseCase(getUserFromFirebaseUseCase)

}