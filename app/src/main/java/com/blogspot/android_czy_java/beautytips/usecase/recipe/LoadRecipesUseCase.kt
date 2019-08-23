package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import io.reactivex.Single

open class LoadRecipesUseCase<RECIPE_REQUEST>(private val recipeRepository: RecipeRepositoryInterface<RECIPE_REQUEST>):
        UseCaseInterface<RECIPE_REQUEST, List<RecipeModel>> {


    override fun execute(request: RECIPE_REQUEST): Single<List<RecipeModel>> =
             recipeRepository.getRecipes(request)

}