package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.*
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase

class CreateRecipeRequestsUseCase : NestedListRequestUseCase<RecipeRequest> {

    override fun execute() = MainListRequest(arrayListOf(
            (RecipeRequest(CategoryAll.SUBCATEGORY_ALL, Order.NEW)),
            (RecipeRequest(CategoryAll.SUBCATEGORY_ALL, Order.POPULARITY)),
            (RecipeRequest(CategoryHair.SUBCATEGORY_ALL, Order.NEW)),
            (RecipeRequest(CategoryFace.SUBCATEGORY_ALL, Order.NEW)),
            (RecipeRequest(CategoryBody.SUBCATEGORY_ALL, Order.NEW))
    ))


    override fun getOneRequest(listId: Int): NestedListRequest<RecipeRequest> {
        return MainListRequest(execute().requests[listId])
    }
}