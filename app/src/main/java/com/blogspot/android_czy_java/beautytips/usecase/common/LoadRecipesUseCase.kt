package com.blogspot.android_czy_java.beautytips.usecase.common

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListData
import io.reactivex.Single

open class LoadRecipesUseCase<RECIPE_REQUEST : OneListRequest>(private val recipeRepository: RecipeRepositoryInterface<RECIPE_REQUEST>) :
        UseCaseInterface<RECIPE_REQUEST, OneListData> {


    override fun execute(request: RECIPE_REQUEST): Single<OneListData> =
            recipeRepository.getRecipes(request)


}