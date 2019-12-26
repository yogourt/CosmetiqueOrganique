package com.blogspot.android_czy_java.beautytips.usecase.notification

import com.blogspot.android_czy_java.beautytips.service.notification.NotificationKeys
import com.blogspot.android_czy_java.beautytips.usecase.account.GetUserFromFirebaseUseCase
import io.reactivex.Single

class ProcessNotificationUseCase(private val getUserFromFirebaseUseCase: GetUserFromFirebaseUseCase) {

    fun getTitle(notificationType: String?): String? {

        return when (notificationType?.toIntOrNull()) {
            NotificationKeys.NOTIFICATION_TYPE_COMMENT_ON_USER_RECIPE ->
                "New comment on your recipe"
            NotificationKeys.NOTIFICATION_TYPE_SUBCOMMENT_ON_USER_COMMENT ->
                "New subcomment on your comment"
            else -> null
        }
    }

    fun getMessage(data: MutableMap<String, String>): Single<String>? {

        val type = data[NotificationKeys.KEY_NOTIFICATION_TYPE]?.toIntOrNull()

        val message = data[NotificationKeys.KEY_MESSAGE]

        if (message.isNullOrEmpty()) return null

        return when (type) {
            NotificationKeys.NOTIFICATION_TYPE_COMMENT_ON_USER_RECIPE,
            NotificationKeys.NOTIFICATION_TYPE_SUBCOMMENT_ON_USER_COMMENT -> {
                val authorId = data[NotificationKeys.KEY_AUTHOR_ID]
                authorId?.let {
                    return getUserFromFirebaseUseCase.execute(it).flatMap {user ->
                        Single.create<String> { it.onSuccess("${user.nickname}: $message") }
                    }
                } ?: Single.create { it.onSuccess(message) }
            }
            else -> Single.create { it.onSuccess(message) }
        }
    }


}