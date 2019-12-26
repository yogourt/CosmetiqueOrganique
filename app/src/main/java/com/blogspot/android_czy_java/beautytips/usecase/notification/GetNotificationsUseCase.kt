package com.blogspot.android_czy_java.beautytips.usecase.notification

import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.exception.account.UserNotFoundException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.notification.NotificationRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.GetUserFromFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.comment.CommentWithAuthorModel
import io.reactivex.Single

class GetNotificationsUseCase(private val notificationRepository: NotificationRepository,
                              private val currentUserUseCase: GetCurrentUserUseCase,
                              private val getUserFromFirebaseUseCase: GetUserFromFirebaseUseCase) {

    fun execute(): Single<List<NotificationModel>> {
        return Single.create { emitter ->

            currentUserUseCase.currentUserId()?.let { userId ->
                val notifications =
                        notificationRepository.getNotificationsForUser(userId)

                val singleToZip = createUserRequests(notifications)

                Single.zip(singleToZip) { results ->
                    for (result in results) {
                        if (result is UserModel) {
                            notifications.map {
                                if (it.authorId == result.id) {
                                    it.message = "${result.nickname}: ${it.message}"
                                }
                            }
                        }
                    }
                    notifications
                }.subscribe { newList ->
                    emitter.onSuccess(newList)
                }

            } ?: emitter.onError(UserNotFoundException())

        }
    }

    private fun createUserRequests(notifications: List<NotificationModel>): List<Single<UserModel>> {
        val singleToZip = mutableListOf<Single<UserModel>>()

        val alreadyAddedIds = mutableListOf<String>()

        for (notification in notifications) {
            notification.authorId?.let {
                if (!alreadyAddedIds.contains(it))
                    singleToZip.add(getUserFromFirebaseUseCase.execute(it))
                alreadyAddedIds.add(it)
            }
        }
        return singleToZip
    }
}