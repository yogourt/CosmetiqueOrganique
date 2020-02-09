package com.blogspot.android_czy_java.beautytips.usecase.notification

import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository
import com.blogspot.android_czy_java.beautytips.service.notification.NotificationKeys
import com.google.firebase.messaging.RemoteMessage

class SaveNotificationUseCase(private val notificationRepository: NotificationRepository,
                              private val recipeDetailRepository: RecipeDetailRepository,
                              private val commentRepository: CommentRepository) {

    fun execute(request: RemoteMessage) {

        Thread(Runnable {
            var notification: NotificationModel

            request.data.let {
                val message = it[NotificationKeys.KEY_MESSAGE] ?: return@Runnable
                val recipeId = it[NotificationKeys.KEY_RECIPE_ID]?.toLongOrNull()
                var image = it[NotificationKeys.KEY_IMAGE]
                val commentId = it[NotificationKeys.KEY_COMMENT_ID]
                val userId = it[NotificationKeys.KEY_USER_ID] ?: return@Runnable
                val authorId = it[NotificationKeys.KEY_AUTHOR_ID]
                val firebaseId = it[NotificationKeys.KEY_FIREBASE_COMMENT_ID] ?: return@Runnable
                val type = it[NotificationKeys.KEY_NOTIFICATION_TYPE]?.toInt() ?: return@Runnable

                if (image.isNullOrEmpty() && recipeId != null) {
                    image = getRecipeImage(recipeId)
                }

                notification = NotificationModel(authorId, message, image, recipeId, commentId, userId, type)
                notificationRepository.insertNotification(notification)

                when (it[NotificationKeys.KEY_NOTIFICATION_TYPE]?.toInt()) {
                    NotificationKeys.NOTIFICATION_TYPE_COMMENT_ON_USER_RECIPE,
                    NotificationKeys.NOTIFICATION_TYPE_SUBCOMMENT_ON_USER_COMMENT -> {

                        tryToSaveComment(recipeId, authorId, firebaseId, commentId, message)
                    }
                }

            }

        }).start()

    }

    private fun tryToSaveComment(recipeId: Long?, authorId: String?, firebaseId: String,
                                 responseTo: String?, message: String) {
        if (recipeId != null && authorId != null)
            commentRepository.addComment(
                    CommentModel(firebaseId, responseTo, recipeId, authorId, message)
            )
    }

    private fun getRecipeImage(recipeId: Long) = recipeDetailRepository.getImageUrl(recipeId)

}