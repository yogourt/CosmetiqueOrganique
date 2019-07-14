package com.blogspot.android_czy_java.beautytips.database.userlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserLists")
data class UserListModel(
        var listName: String,
        var recipesInList: String
        ) {

    @PrimaryKey(autoGenerate = true)
    var listId = 0L

    var iconResourceId: String? = null
}