package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailIngredient

class DetailIngredientConverter(private val ingredient: String,
                                private val isIngredientOptional: Boolean) {

    fun convert(): DetailIngredient {
        val data = ingredient.split(":")
        val name = data[0].trim()
        val quantity = data[1].trim()
        return DetailIngredient(name, quantity, isIngredientOptional)
    }
}