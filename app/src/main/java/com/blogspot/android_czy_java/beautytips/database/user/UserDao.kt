package com.blogspot.android_czy_java.beautytips.database.user

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserModel)

    @Query("SELECT * FROM User")
    fun getUsers(): List<UserModel>

    @Query("SELECT * FROM User WHERE id = :firebaseId")
    fun getUserByFirebaseId(firebaseId: String): UserModel?

    @Update
    fun updateUser(user: UserModel)
}