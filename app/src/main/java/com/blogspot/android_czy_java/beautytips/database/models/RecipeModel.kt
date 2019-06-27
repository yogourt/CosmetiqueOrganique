package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName="Recipes")
data class RecipeModel(
        @PrimaryKey
        var recipeId: Long,
        var title: String,
        var image: String,
        var authorId: String,
        var category: String,
        var subcategory: String,
        var favNum: Long,
        @Embedded
        var details: RecipeDetailModel
)
