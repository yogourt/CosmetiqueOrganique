package com.blogspot.android_czy_java.beautytips.database.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.languages.Language
import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.database.DataSnapshot
import kotlin.math.absoluteValue

class RecipeConverter(private val recipeDataSnapshot: DataSnapshot) {

    fun getRecipe(): RecipeModel? {
        val recipeId = recipeDataSnapshot.key ?: return null
        val title = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_TITLE).value.toString()
        val image = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_IMAGE).value?.toString()
                ?: return null
        val authorId = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_AUTHOR_ID).value?.toString()
        var category = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_CATEGORY).value.toString()
        var subcategory = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_SUBCATEGORY).value.toString()
        val favNumDb = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_FAV_NUM).value
        val tags = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_TAGS).value.toString()
        val language = recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_LANGUAGE).value?.toString()
                ?: Language.ENGLISH.code

        if (!category.startsWith("For")) {
            category = "For $category"
        }
        subcategory = subcategory.capitalize()

        val favNum = (favNumDb?.toString()?.toLong() ?: 0L).absoluteValue

        return RecipeModel(
                recipeId.toLong(),
                title,
                image,
                authorId,
                category,
                subcategory,
                favNum,
                tags,
                language
        )
    }
}