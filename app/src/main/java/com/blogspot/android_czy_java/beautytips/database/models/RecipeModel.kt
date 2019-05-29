package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

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
        @Embedded
        val recipeDetail: RecipeDetailModel)
