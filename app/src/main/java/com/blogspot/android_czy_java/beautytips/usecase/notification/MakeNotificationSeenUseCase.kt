package com.blogspot.android_czy_java.beautytips.usecase.notification

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository

class MakeNotificationSeenUseCase(private val notificationRepository: NotificationRepository) {

    fun execute(notificationId: Int) {
        Thread(Runnable {
            notificationRepository.makeNotificationSeen(notificationId)
        }).start()
    }
}