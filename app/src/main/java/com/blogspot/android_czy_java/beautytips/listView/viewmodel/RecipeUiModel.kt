package com.blogspot.android_czy_java.beautytips.listView.viewmodel

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel

sealed class RecipeUiModel {

    class RecipeLoading: RecipeUiModel()

    data class RecipeSuccess(val recipes: List<RecipeMappedModel>): RecipeUiModel()

    data class RecipeLoadingError(val message: String): RecipeUiModel()

}