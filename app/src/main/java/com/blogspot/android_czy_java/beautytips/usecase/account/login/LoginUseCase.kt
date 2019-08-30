package com.blogspot.android_czy_java.beautytips.usecase.account.login

import com.google.firebase.auth.FirebaseAuth

class LoginUseCase {

    fun loginAnonymously() = FirebaseAuth.getInstance().signInAnonymously().isSuccessful

    fun loginAnonymouslyIfNull() {
        if(FirebaseAuth.getInstance().currentUser == null) loginAnonymously()
    }

}