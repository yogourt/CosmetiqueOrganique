package com.blogspot.android_czy_java.beautytips.database.recipe

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blogspot.android_czy_java.beautytips.database.ItemModelInterface
import com.blogspot.android_czy_java.beautytips.database.detail.RecipeDetailModel

@Entity(tableName="Recipes")
data class RecipeModel(
        @PrimaryKey
        var recipeId: Long,
        override var title: String,
        override var imageUrl: String,
        var authorId: String?,
        var category: String,
        var subcategory: String,
        var favNum: Long,
        var tags: String
): ItemModelInterface {
        @Embedded
        lateinit var details: RecipeDetailModel

        override var id = recipeId

}
