package com.blogspot.android_czy_java.beautytips.usecase.notification

import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel
import com.blogspot.android_czy_java.beautytips.exception.account.UserNotFoundException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import io.reactivex.Single

class GetNotificationsUseCase(private val notificationRepository: NotificationRepository,
                              private val currentUserUseCase: GetCurrentUserUseCase) {

    fun execute(): Single<List<NotificationModel>> {
        return Single.create { emitter ->

            currentUserUseCase.currentUserId()?.let { userId ->
                emitter.onSuccess(notificationRepository.getNotificationsForUser(userId))
            } ?: emitter.onError(UserNotFoundException())

        }
    }
}