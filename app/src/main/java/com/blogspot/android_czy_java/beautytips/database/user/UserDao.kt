package com.blogspot.android_czy_java.beautytips.database.user

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT firebaseId FROM User WHERE id = 0")
    fun getUserFirebaseId(): String
}