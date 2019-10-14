package com.blogspot.android_czy_java.beautytips.database.comment

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude

@Entity(tableName = "RecipeComments",
        indices = [
            Index(value = ["firebaseId"], unique = true)
        ])
data class CommentModel(
        @Exclude
        var firebaseId: String?,
        var responseTo: String?,
        @Exclude
        var recipeId: Long,
        var authorId: String,
        var authorNickname: String,
        var message: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}