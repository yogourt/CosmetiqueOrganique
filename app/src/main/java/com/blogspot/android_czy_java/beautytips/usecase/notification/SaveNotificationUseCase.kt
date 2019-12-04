package com.blogspot.android_czy_java.beautytips.usecase.notification

import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository
import com.blogspot.android_czy_java.beautytips.service.notification.NotificationKeys
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.google.firebase.messaging.RemoteMessage

class SaveNotificationUseCase(private val notificationRepository: NotificationRepository,
                              private val recipeDetailRepository: RecipeDetailRepository,
                              private val currentUserUseCase: GetCurrentUserUseCase) {

    fun execute(request: RemoteMessage) {

        Thread(Runnable {
            var notification: NotificationModel

            request.data.let {
                val message = it[NotificationKeys.KEY_MESSAGE] ?: return@Runnable
                val recipeId = it[NotificationKeys.KEY_RECIPE_ID]?.toLongOrNull()
                var image = it[NotificationKeys.KEY_IMAGE]
                val commentId = it[NotificationKeys.KEY_COMMENT_ID]
                val userId = it[NotificationKeys.KEY_USER_ID] ?: return@Runnable

                if (image.isNullOrEmpty() && recipeId != null) {
                    image = getRecipeImage(recipeId)
                }

                notification = NotificationModel(message, image, recipeId, commentId, userId)
            }

        }).start()

    }

    private fun getRecipeImage(recipeId: Long) = recipeDetailRepository.getImageUrl(recipeId)

}