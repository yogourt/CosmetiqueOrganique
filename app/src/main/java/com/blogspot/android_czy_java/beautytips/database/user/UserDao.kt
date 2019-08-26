package com.blogspot.android_czy_java.beautytips.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: UserModel)

    @Query("UPDATE User SET isLoggedIn = 1 WHERE id = :userId ")
    fun logIn(userId: Long)

    @Query("UPDATE User SET isLoggedIn = 0 WHERE id = :userId ")
    fun logOut(userId: Long)

    @Query("SELECT firebaseId FROM User WHERE isLoggedIn")
    fun getUserFirebaseId(): String
}