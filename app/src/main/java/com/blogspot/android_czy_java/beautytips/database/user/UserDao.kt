package com.blogspot.android_czy_java.beautytips.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserModel)

    @Query("SELECT * FROM User")
    fun getUsers(): List<UserModel>

    @Query("SELECT * FROM User WHERE id = :firebaseId")
    fun getUserByFirebaseId(firebaseId: String): UserModel
}