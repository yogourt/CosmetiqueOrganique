package com.blogspot.android_czy_java.beautytips.database.userlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserListDao {

    @Query("SELECT * FROM UserLists WHERE userId=:userId")
    fun getAllUserLists(userId: String): List<UserListModel>

    @Query("SELECT recipesInList FROM UserLists WHERE userId=:userId AND listName=:listName")
    fun getListByUserIdAndName(userId: String, listName: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: UserListModel)

}