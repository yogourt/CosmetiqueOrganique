package com.blogspot.android_czy_java.beautytips.database.detail

import androidx.room.Entity

@Entity
data class RecipeDetailModel (
    var description: String,
    var source: String? = null,
    var ingredients: String,
    var optionalIngredients: String
)