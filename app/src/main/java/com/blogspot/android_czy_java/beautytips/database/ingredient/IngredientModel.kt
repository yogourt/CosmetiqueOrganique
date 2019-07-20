package com.blogspot.android_czy_java.beautytips.database.ingredient

import androidx.room.PrimaryKey

data class IngredientModel(
        @PrimaryKey
        var ingredientId: Long,
        var title: String,
        var imageUrl: String,
        var category: String,
        var tags: String
)