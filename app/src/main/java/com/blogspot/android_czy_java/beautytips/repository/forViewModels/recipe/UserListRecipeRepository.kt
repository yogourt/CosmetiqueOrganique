package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListDao
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListModel
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
            emitter.onSuccess(OneListData(recipes, request.userList))
        }
    }

    fun addToUserList(listName: String, recipeId: Long, userId: String) {
        val favs = getRecipeIdsInList(userId, listName)
                .toMutableList()
        favs.add(recipeId)
        updateRecipesInUserList(listName, userId, favs.joinToString())

        if(listName == FirebaseKeys.KEY_USER_LIST_FAVORITES) {
            recipeDao.incrementFavNum(recipeId)
        }
    }

    fun removeFromUserList(listName: String, recipeId: Long, userId: String) {
        val favs = getRecipeIdsInList(userId, listName)
                .toMutableList()
        favs.remove(recipeId)
        updateRecipesInUserList(listName, userId, favs.joinToString(","))

        if(listName == FirebaseKeys.KEY_USER_LIST_FAVORITES) {
            recipeDao.decrementFavNum(recipeId)
        }
    }

    fun getRecipeIdsInList(userId: String, listName: String): List<Long> {
        return userListDao.getListByUserIdAndName(userId, listName)
                ?.split(",")?.mapNotNull { it.trim().toLongOrNull() } ?: mutableListOf()
    }

    private fun updateRecipesInUserList(listName: String, userId: String, recipes: String) {
        userListDao.updateRecipesInUserList(listName, userId, recipes)
    }

    fun createUserList(userList: UserListModel) {
        userListDao.insertList(userList)
    }

    fun getAllUserListTitles(userId: String) = userListDao.getAllUserListTitles(userId)
}