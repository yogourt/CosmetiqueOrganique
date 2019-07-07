package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import io.reactivex.Single

interface RecipeRepositoryInterface<REQUEST> {

    fun  getRecipes(request: REQUEST):  Single<List<RecipeModel>>
}