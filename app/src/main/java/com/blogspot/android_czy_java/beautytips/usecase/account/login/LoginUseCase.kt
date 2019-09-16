package com.blogspot.android_czy_java.beautytips.usecase.account.login

import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.exception.account.UserNotFoundException
import com.blogspot.android_czy_java.beautytips.exception.account.UserNotLoggedInException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.error.ErrorRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.UpdateUserDataInFirebaseUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single

class LoginUseCase(private val userRepository: UserRepository,
                   private val errorRepository: ErrorRepository,
                   private val updateUserDataInFirebaseUseCase: UpdateUserDataInFirebaseUseCase) {

    fun isUserAnonymousOrNull(): Boolean? {
        return FirebaseAuth.getInstance().currentUser?.isAnonymous
    }

    fun isUserNull() = FirebaseAuth.getInstance().currentUser == null

    fun loginAnonymously(): Boolean {

        if (isUserLoggedIn()) FirebaseAuth.getInstance().signOut()
        return FirebaseAuth.getInstance().signInAnonymously().isSuccessful
    }

    private fun isUserLoggedIn(): Boolean = !(FirebaseAuth.getInstance().currentUser?.isAnonymous
            ?: true)


    fun loginAnonymouslyIfNull() {
        if (FirebaseAuth.getInstance().currentUser == null) loginAnonymously()
    }

    fun saveAndReturnUser(): Single<UserModel> =
            Single.create { emitter ->
                getCurrentUserFirebaseId()?.let { id ->
                    if (userShouldBeInserted(id)) {
                        userRepository.insertCurrentFirebaseUser(id, emitter)
                    } else {
                        //return from local db
                        userRepository.getUserByFirebaseId(id)?.let {
                            emitter.onSuccess(it)
                        } ?: emitter.onError(UserNotFoundException())
                    }
                } ?: emitter.onError(UserNotLoggedInException())
            }

    private fun userShouldBeInserted(firebaseId: String): Boolean {
        return userRepository.getUserByFirebaseId(firebaseId) == null ||
                errorRepository.getError(ErrorModel("Users", firebaseId)) == null
    }

    fun updateUserData(user: UserModel): Single<UserModel> =
            Single.create {
                userRepository.updateUser(user)
                updateUserDataInFirebaseUseCase.execute(user.id)
                it.onSuccess(user)
            }

    private fun getCurrentUserFirebaseId() = FirebaseAuth.getInstance().currentUser?.uid
}
