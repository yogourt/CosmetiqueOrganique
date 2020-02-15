package com.blogspot.android_czy_java.beautytips.database.userlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface UserListDao {

    @Query("SELECT * FROM UserLists WHERE userId=:userId")
    fun getAllUserLists(userId: String): List<UserListModel>?

    @Query("SELECT listName FROM UserLists WHERE userId=:userId")
    fun getAllUserListTitles(userId: String): List<String>?

    @Query("SELECT recipesInList FROM UserLists WHERE userId=:userId AND listName=:listName")
    fun getListByUserIdAndName(userId: String, listName: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: UserListModel)

    @Query("UPDATE UserLists SET recipesInList=:recipes WHERE userId=:userId AND listName=:listName")
    fun updateRecipesInUserList(listName: String, userId: String, recipes: String)

    @Query("SELECT * FROM UserLists")
    fun getAllLists(): List<UserListModel>?
}
