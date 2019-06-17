package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName="Recipes")
data class RecipeModel(
        @PrimaryKey
        val recipeId: Long,
        val title: String,
        val image: String,
        val authorId: String,
        val category: String,
        val subcategory: String,
        val favNum: Long,

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
