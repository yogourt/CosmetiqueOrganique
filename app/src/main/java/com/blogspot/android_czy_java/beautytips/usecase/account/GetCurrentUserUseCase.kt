package com.blogspot.android_czy_java.beautytips.usecase.account

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.exception.account.UserNotFoundException
import com.blogspot.android_czy_java.beautytips.exception.account.UserNotLoggedInException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.blogspot.android_czy_java.beautytips.usecase.common.NoRequestUseCaseInterface
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single

class GetCurrentUserUseCase(private val userRepository: UserRepository) :
        NoRequestUseCaseInterface<UserModel> {

    override fun execute(): Single<UserModel> =
            Single.create {
                val userFirebaseId = FirebaseAuth.getInstance().currentUser?.uid

                if (userFirebaseId == null) {
                    it.onError(UserNotLoggedInException())
                } else {
                    val user = userRepository.getUserByFirebaseId(userFirebaseId)

                    if (user != null) {
                        it.onSuccess(user)
                    } else {
                        it.onError(UserNotFoundException())
                    }
                }
            }

    fun currentUserId() = FirebaseAuth.getInstance().currentUser?.uid

}