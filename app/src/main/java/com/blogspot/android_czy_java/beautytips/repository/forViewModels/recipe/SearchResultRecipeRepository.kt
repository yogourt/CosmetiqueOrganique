package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
import io.reactivex.Single

class SearchResultRecipeRepository(recipeDao: RecipeDao) :
        RecipeRepositoryInterface<SearchResultRequest>(recipeDao) {

    override fun getRecipes(request: SearchResultRequest): Single<List<RecipeModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}