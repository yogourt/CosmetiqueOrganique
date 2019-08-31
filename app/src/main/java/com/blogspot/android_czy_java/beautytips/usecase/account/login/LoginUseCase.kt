package com.blogspot.android_czy_java.beautytips.usecase.account.login

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single

class LoginUseCase {

    fun isUserAnonymousOrNull(): Boolean? {
        return FirebaseAuth.getInstance().currentUser?.isAnonymous
    }

    fun loginAnonymously() = FirebaseAuth.getInstance().signInAnonymously().isSuccessful

    fun loginAnonymouslyIfNull() {
        if (FirebaseAuth.getInstance().currentUser == null) loginAnonymously()
    }

    fun saveAndReturnUser(firebaseId: Long): Single<UserModel> =
        Single.create {
            FirebaseDatabase.getInstance().reference
        }


}