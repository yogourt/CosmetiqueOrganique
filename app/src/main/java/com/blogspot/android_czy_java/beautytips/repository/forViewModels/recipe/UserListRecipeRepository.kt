package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListDao
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import io.reactivex.Single

class UserListRecipeRepository(private val recipeDao: RecipeDao,
                               private val userListDao: UserListDao) :
        RecipeRepositoryInterface<UserListRecipeRequest>(recipeDao) {

    override fun getRecipes(request: UserListRecipeRequest): Single<List<RecipeModel>> {
        return Single.create { emitter ->

            val recipes = when (request.order) {
                Order.NEW -> recipeDao.getAllRecipes()
                Order.POPULARITY -> recipeDao.getAllRecipesOrderByPopularity()
            }.filter {
                userListDao.getListByUserIdAndName(request.userId, request.userList)
                        ?.split(",")
                        ?.map { it.trim() }
                        ?.contains(it.recipeId.toString()) ?: false
            }
            emitter.onSuccess(recipes)
        }
    }
}