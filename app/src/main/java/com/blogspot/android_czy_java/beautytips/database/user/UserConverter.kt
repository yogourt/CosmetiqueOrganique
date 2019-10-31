package com.blogspot.android_czy_java.beautytips.database.user

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.database.DataSnapshot

class UserConverter(private val userSnapshot: DataSnapshot) {

    fun execute(): UserModel? {

        val firebaseId = userSnapshot.key ?: return null

        val nickname = userSnapshot.child(FirebaseKeys.KEY_USER_NICKNAME).value?.toString()
                ?: return null

        val photo = userSnapshot.child(FirebaseKeys.KEY_USER_PHOTO).value?.toString() ?: ""

        val about = userSnapshot.child(FirebaseKeys.KEY_USER_ABOUT).value?.toString() ?: ""

        return UserModel(firebaseId, nickname, photo, about)
    }
}