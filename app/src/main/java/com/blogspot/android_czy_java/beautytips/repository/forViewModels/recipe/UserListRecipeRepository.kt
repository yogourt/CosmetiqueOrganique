package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.usecase.recipe.UserListRecipeRequest
import io.reactivex.Single

class UserListRecipeRepository(private val recipeDao: RecipeDao) :
        RecipeRepositoryInterface<UserListRecipeRequest> {

    override fun getRecipes(request: UserListRecipeRequest): Single<List<RecipeModel>> {
        return Single.create { emitter ->

            val recipes = when (request.order) {
                Order.NEW -> recipeDao.getAllRecipes()
                Order.POPULARITY -> recipeDao.getAllRecipesOrderByPopularity()
            }
            emitter.onSuccess(recipes)
        }
    }
}