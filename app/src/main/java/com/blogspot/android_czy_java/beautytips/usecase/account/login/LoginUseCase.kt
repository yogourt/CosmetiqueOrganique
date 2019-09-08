package com.blogspot.android_czy_java.beautytips.usecase.account.login

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.exception.account.UserNotLoggedInException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Single

class LoginUseCase(private val userRepository: UserRepository) {

    fun isUserAnonymousOrNull(): Boolean? {
        return FirebaseAuth.getInstance().currentUser?.isAnonymous
    }

    fun isUserNull() = FirebaseAuth.getInstance().currentUser == null

    fun loginAnonymously(): Boolean {

        if (isUserLoggedIn()) FirebaseAuth.getInstance().signOut()
        return FirebaseAuth.getInstance().signInAnonymously().isSuccessful
    }

    private fun isUserLoggedIn(): Boolean = !(FirebaseAuth.getInstance().currentUser?.isAnonymous ?: true)


    fun loginAnonymouslyIfNull() {
        if (FirebaseAuth.getInstance().currentUser == null) loginAnonymously()
    }

    fun saveAndReturnUser(): Single<UserModel> =
            Single.create {
                getCurrentUserFirebaseId()?.let { id ->
                    userRepository.insertCurrentFirebaseUser(id, it)
                } ?: it.onError(UserNotLoggedInException())
            }

    private fun getCurrentUserFirebaseId() = FirebaseAuth.getInstance().currentUser?.uid
}
