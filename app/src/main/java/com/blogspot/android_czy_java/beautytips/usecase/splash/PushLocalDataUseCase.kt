package com.blogspot.android_czy_java.beautytips.usecase.splash

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.error.ErrorRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.UpdateUserDataInFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.PushUserListToFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.comment.PushCommentToFirebaseUseCase
import io.reactivex.Single

class PushLocalDataUseCase(private val commentRepository: CommentRepository,
                           private val pushCommentToFirebaseUseCase: PushCommentToFirebaseUseCase,
                           private val errorRepository: ErrorRepository,
                           private val updateUserDataInFirebaseUseCase: UpdateUserDataInFirebaseUseCase,
                           private val userRepository: UserRepository,
                           private val pushUserListToFirebaseUseCase: PushUserListToFirebaseUseCase) {

    fun execute(): Single<Boolean> {

        return Single.create {
            //push comments
            val commentsToPush = commentRepository.getCommentsToPush()
            if (commentsToPush != null) {
                for (comment in commentsToPush) {
                    pushCommentToFirebaseUseCase.execute(comment)
                }
            }

            //push user info
            val userErrors = errorRepository.getAllUserErrors()
            if (userErrors != null) {
                for (error in userErrors) {
                    updateUserDataInFirebaseUseCase.execute(error.rowId)
                }
            }

            //push user lists
            val userLists = userRepository.getAllLists()
            if(userLists != null) {
                for(list in userLists) {
                    pushUserListToFirebaseUseCase.execute(list)
                }
            }

            it.onSuccess(true)

        }
    }

}