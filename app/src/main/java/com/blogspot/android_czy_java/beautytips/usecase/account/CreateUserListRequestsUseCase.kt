package com.blogspot.android_czy_java.beautytips.usecase.account

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryBody
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryFace
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryHair
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.MainListRequest
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest

class CreateUserListRequestsUseCase: NestedListRequestUseCase<UserListRecipeRequest> {
    override fun execute(): NestedListRequest<UserListRecipeRequest> {
        val requests = arrayListOf<UserListRecipeRequest>()

        requests.add(UserListRecipeRequest("Favorites", Order.POPULARITY))

        return UserListRequest(requests)
    }
}