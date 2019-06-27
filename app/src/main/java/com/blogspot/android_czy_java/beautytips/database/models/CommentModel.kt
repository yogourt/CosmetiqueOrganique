package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName="RecipeComments")
data class CommentModel(
        @PrimaryKey
        var id: Long,
        var recipeId: Long,
        var authorId: String,
        var description: String,
        var authorNickname: String
) {
}