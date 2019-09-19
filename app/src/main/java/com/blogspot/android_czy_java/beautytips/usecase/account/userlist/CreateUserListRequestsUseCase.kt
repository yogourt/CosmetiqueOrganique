package com.blogspot.android_czy_java.beautytips.usecase.account.userlist

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase

class CreateUserListRequestsUseCase(private val currentUserUseCase: GetCurrentUserUseCase) :
        NestedListRequestUseCase<UserListRecipeRequest> {
    override fun execute(): NestedListRequest<UserListRecipeRequest> {
        val requests = arrayListOf<UserListRecipeRequest>()

        currentUserUseCase.currentUserId()?.let {
            requests.add(UserListRecipeRequest("favorites", it, Order.NEW))
            requests.add(UserListRecipeRequest("my_recipes", it, Order.NEW))
        }

        return UserListRequest(requests)
    }

    override fun getOneRequest(listId: Int): NestedListRequest<UserListRecipeRequest> {
        return UserListRequest(execute().requests[listId])
    }
}