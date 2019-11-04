package com.blogspot.android_czy_java.beautytips.database.detail

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.database.DataSnapshot

class DetailConverter(private val detailDataSnapshot: DataSnapshot) {

    fun getDetails(): RecipeDetailModel {
        val description = detailDataSnapshot.child(FirebaseKeys.KEY_RECIPE_DESCRIPTION).value.toString()
        val source = detailDataSnapshot.child(FirebaseKeys.KEY_RECIPE_SOURCE).value
        val ingredients = detailDataSnapshot.child(FirebaseKeys.KEY_RECIPE_INGREDIENTS).value.toString()
        val optionalIngredients = detailDataSnapshot
                .child(FirebaseKeys.KEY_RECIPE_OPTIONAL_INGREDIENTS)
                .value?.toString() ?: ""


        return if (source == null) {
            RecipeDetailModel(
                    description = description,
                    ingredients = ingredients,
                    optionalIngredients = optionalIngredients)
        } else {
            RecipeDetailModel(
                    description,
                    source.toString(),
                    ingredients,
                    optionalIngredients)
        }
    }
}