package com.blogspot.android_czy_java.beautytips.database.comment

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName="RecipeComments")
data class CommentModel(
        @PrimaryKey
        var id: String,
        var responseTo: String?,
        var recipeId: Long,
        var authorId: String,
        var authorNickname: String,
        var message: String
)