package com.blogspot.android_czy_java.beautytips.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "User")
data class UserModel(
        @PrimaryKey
        val id: String,
        val nickname: String,
        val photo: String,
        val about: String = ""
): Serializable