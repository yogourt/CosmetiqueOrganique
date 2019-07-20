package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailIngredient

class DetailIngredientsConverter(private val ingredients: List<String>,
                                 private val areIngredientsOptional: Boolean) {

    fun convert(): List<DetailIngredient> {

        val convertedIngredients = mutableListOf<DetailIngredient>()

        for(ingredient in ingredients) {
            convertedIngredients.add(
                    DetailIngredientConverter(ingredient, areIngredientsOptional)
                            .convert()
            )
        }
        return convertedIngredients
    }

}