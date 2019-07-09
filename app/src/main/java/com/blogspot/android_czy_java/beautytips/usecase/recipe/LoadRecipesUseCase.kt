package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.UseCaseInterface
import io.reactivex.Single

class LoadRecipesUseCase(private val recipeRepository: RecipeRepositoryInterface<RecipeRequest>):
        UseCaseInterface<RecipeRequest, List<RecipeModel>> {


    override fun execute(request: RecipeRequest): Single<List<RecipeModel>> =
             recipeRepository.getRecipes(request)

}