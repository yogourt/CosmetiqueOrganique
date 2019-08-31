package com.blogspot.android_czy_java.beautytips.database.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserModel(
        @PrimaryKey
        val id: String,
        val nickname: String,
        val photo: String
)