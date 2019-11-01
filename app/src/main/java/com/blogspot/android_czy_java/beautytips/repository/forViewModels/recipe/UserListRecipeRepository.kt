package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListDao
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListData
import io.reactivex.Single

class UserListRecipeRepository(override val recipeDao: RecipeDao,
                               private val userListDao: UserListDao) :
        RecipeRepositoryInterface<UserListRecipeRequest>(recipeDao) {

    override fun getRecipes(request: UserListRecipeRequest): Single<OneListData> {
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
            emitter.onSuccess(OneListData(recipes, createListTitle(request)))
        }
    }

    private fun createListTitle(recipeRequest: UserListRecipeRequest): String {
        return recipeRequest.userList.capitalize().replace("_", " ")
    }

    fun getRecipeIdsInList(userId: String, listName: String): List<Long> {
        return userListDao.getListByUserIdAndName(userId, listName)
                ?.split(",")
                ?.map { it.trim().toLong() } ?: listOf()
    }
}