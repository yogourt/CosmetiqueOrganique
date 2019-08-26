package com.blogspot.android_czy_java.beautytips.database.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserModel(
        val firebaseId: String,
        val nickname: String,
        val photo: String
) {
    @PrimaryKey
    var id = 0

    var isLoggedIn = false
}