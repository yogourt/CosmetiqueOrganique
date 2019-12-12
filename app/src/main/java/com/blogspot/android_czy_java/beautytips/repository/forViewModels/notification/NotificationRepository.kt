package com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification

import com.blogspot.android_czy_java.beautytips.database.notification.NotificationDao
import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel

class NotificationRepository(private val notificationDao: NotificationDao) {

    fun getNotificationsForUser(userId: String) =
            notificationDao.getNotificationsForUser(userId)

    fun insertNotification(notification: NotificationModel) =
            notificationDao.insertNotification(notification)

}