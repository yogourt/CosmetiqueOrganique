package com.blogspot.android_czy_java.beautytips.usecase.common

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import java.io.Serializable

sealed class OneListRequest(open val category: CategoryInterface, open val order: Order) : Serializable {

    fun newOrder(order: Order): OneListRequest {
        return when (this) {
            is RecipeRequest -> RecipeRequest(category, order)
            is UserListRecipeRequest -> UserListRecipeRequest(userList, userId, order)
            is SearchResultRequest -> SearchResultRequest(category, order, language, title, author, keywords)
        }
    }
}

class UserListRecipeRequest(val userList: String, val userId: String, override val order: Order)
    : OneListRequest(CategoryAll.SUBCATEGORY_ALL, order) {
    val userListTitle = userList.replace("_", " ");
}

class RecipeRequest(override val category: CategoryInterface, override val order: Order) :
        OneListRequest(category, order)

class SearchResultRequest(
        override val category: CategoryInterface,
        override val order: Order,
        val language: String,
        val title: String,
        val author: String,
        val keywords: String
) : OneListRequest(category, order)