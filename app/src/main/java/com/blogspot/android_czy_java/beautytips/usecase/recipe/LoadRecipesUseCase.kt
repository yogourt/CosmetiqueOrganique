package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel
import com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.UseCaseInterface
import io.reactivex.Single

class LoadRecipesUseCase(private val recipeRepository: RecipeRepositoryInterface):
        UseCaseInterface<RecipeRequest, List<RecipeMappedModel>> {


    override fun execute(request: RecipeRequest): Single<List<RecipeMappedModel>> {

        return when(request.order) {
            Order.NEW -> recipeRepository.getByDate(request.category)
            Order.POPULARITY -> recipeRepository.getByPopularity(request.category)
        }

    }
}