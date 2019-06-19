package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Entity
import androidx.room.Relation

@Entity
data class RecipeDetailModel (
    val description: String,
    val source: String,
    val commentNum: Long,
    val ingredient1: String,
    val ingredient2: String,
    val ingredient3: String,
    val ingredient4: String,
    @Relation(parentColumn = "recipeId", entityColumn = "recipeId")
    val comments: ArrayList<CommentModel>
)