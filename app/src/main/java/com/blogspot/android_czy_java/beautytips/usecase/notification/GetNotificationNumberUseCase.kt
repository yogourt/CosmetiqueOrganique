package com.blogspot.android_czy_java.beautytips.usecase.notification

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import io.reactivex.Observable

class GetNotificationNumberUseCase(private val notificationRepository: NotificationRepository,
                                   private val currentUserUseCase: GetCurrentUserUseCase) {

    fun execute(): Observable<Int> = currentUserUseCase.currentUserId()?.let { userId ->
        notificationRepository.getUnreadNotificationNumberForUser(userId)
    } ?: Observable.empty()
}