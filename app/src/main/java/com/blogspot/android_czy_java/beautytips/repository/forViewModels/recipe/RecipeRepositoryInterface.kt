package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.usecase.common.OneListRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListData
import io.reactivex.Single

abstract class RecipeRepositoryInterface<REQUEST: OneListRequest>(internal open val recipeDao: RecipeDao) {

    abstract fun getRecipes(request: REQUEST):  Single<OneListData>

    fun getAllRecipesIds() = recipeDao.getAllRecipesIds()
}