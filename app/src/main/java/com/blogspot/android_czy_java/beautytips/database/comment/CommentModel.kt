package com.blogspot.android_czy_java.beautytips.database.comment

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName="RecipeComments")
data class CommentModel(
        var firebaseId: String,
        var recipeId: Long,
        var authorId: String,
        var authorNickname: String,
        var message: String
) {
        @PrimaryKey(autoGenerate = true)
        var id = 0L
}