package com.blogspot.android_czy_java.beautytips.database.recipe

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.database.DataSnapshot

class RecipeConverter(private val recipeDataSnapshot: DataSnapshot) {

    fun getRecipe(): RecipeModel? {
        val recipeId = recipeDataSnapshot.key ?: return null
        val title = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_TITLE).value.toString()
        val image = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_IMAGE).value?.toString() ?: return null
        val authorId = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_AUTHOR_ID).value
        val category = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_CATEGORY).value.toString()
        val subcategory = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_SUBCATEGORY).value.toString()
        val favNumDb = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_FAV_NUM).value
        val tags = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_TAGS).value.toString()

        val favNum = favNumDb?.toString()?.toLong() ?: 0L

        return if (authorId != null) {
            RecipeModel(
                    recipeId.toLong(),
                    title,
                    image,
                    authorId.toString(),
                    category,
                    subcategory,
                    favNum,
                    tags)
        } else {
            RecipeModel(
                    recipeId.toLong(),
                    title,
                    image,
                    null,
                    category,
                    subcategory,
                    favNum,
                    tags)
        }
    }
}