package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="RecipeComments")
data class CommentModel(
        @PrimaryKey
        val id: Long,
        val authorId: String,
        val description: String,
        val authorNickname: String)