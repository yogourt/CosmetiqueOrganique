package com.blogspot.android_czy_java.beautytips.database.ingredient

import androidx.room.PrimaryKey
import com.blogspot.android_czy_java.beautytips.database.ItemModelInterface

data class IngredientModel(
        @PrimaryKey
        var ingredientId: Long,
        override var title: String,
        override var imageUrl: String,
        var category: String,
        var tags: String
) : ItemModelInterface {

    override var id = ingredientId
}