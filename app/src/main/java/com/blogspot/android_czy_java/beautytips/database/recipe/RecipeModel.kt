package com.blogspot.android_czy_java.beautytips.database.recipe

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blogspot.android_czy_java.beautytips.database.detail.RecipeDetailModel

@Entity(tableName = "Recipes")
data class RecipeModel(
        @PrimaryKey
        var recipeId: Long,
        var title: String,
        var imageUrl: String,
        var authorId: String?,
        var category: String,
        var subcategory: String,
        var favNum: Long,
        var tags: String
) {

    var userLists: String = ""

    @Embedded
    lateinit var details: RecipeDetailModel

}
