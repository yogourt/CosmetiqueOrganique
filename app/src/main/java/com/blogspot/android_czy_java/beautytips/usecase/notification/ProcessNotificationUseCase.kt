package com.blogspot.android_czy_java.beautytips.usecase.notification

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
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

        val authorId = data[NotificationKeys.KEY_AUTHOR_ID]
        return createSingleWithProcessedMessage(authorId, message, type)
    }


    private fun createSingleWithProcessedMessage(authorId: String?, message: String, notificationType: Int?): Single<String> =
            authorId?.let {
                return getUserFromFirebaseUseCase.execute(it).flatMap { user ->
                    Single.create<String> {
                        val processedMessage = createMessage(notificationType, user, message)
                        it.onSuccess(processedMessage)
                    }
                }
            } ?: createSingleWithDefaultMessage(message)

    private fun createMessage(notificationType: Int?, user: UserModel, message: String): String {
        return when (notificationType) {
            NotificationKeys.NOTIFICATION_TYPE_COMMENT_ON_USER_RECIPE ->
                "${user.nickname} commented your recipe: $message"

            NotificationKeys.NOTIFICATION_TYPE_SUBCOMMENT_ON_USER_COMMENT ->
                "${user.nickname} commented your comment: $message"

            else -> message
        }
    }

    private fun createSingleWithDefaultMessage(message: String): Single<String> =
            Single.create { it.onSuccess(message) }

}