package com.blogspot.android_czy_java.beautytips.viewmodel.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel

sealed class RecipeUiModel {

    class RecipeLoading: RecipeUiModel()

    data class RecipeSuccess(val recipes: List<RecipeModel>): RecipeUiModel()

    data class RecipeLoadingError(val message: String): RecipeUiModel()

}