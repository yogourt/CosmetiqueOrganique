package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import io.reactivex.Single

abstract class RecipeRepositoryInterface<REQUEST>(internal open val recipeDao: RecipeDao) {

    abstract fun getRecipes(request: REQUEST):  Single<List<RecipeModel>>

    fun getAllRecipesIds() = recipeDao.getAllRecipesIds()
}