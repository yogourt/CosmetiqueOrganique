package com.blogspot.android_czy_java.beautytips.usecase.account

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter

class GetUserFromFirebaseUseCase(private val userRepository: UserRepository) {

    fun execute(firebaseId: String): Single<UserModel> =
            getAuthor(firebaseId)

    private fun getAuthor(firebaseId: String): Single<UserModel> {
        return Single.create { emitter ->
            userRepository.getUserByFirebaseId(firebaseId)?.let {
                emitter.onSuccess(it)
            } ?: getUserFromFirebase(firebaseId, emitter)
        }
    }

    private fun getUserFromFirebase(firebaseId: String,
                                    emitter: SingleEmitter<UserModel>) {
        userRepository.insertFirebaseUser(
                firebaseId,
                emitter,
                false
        )
    }
}