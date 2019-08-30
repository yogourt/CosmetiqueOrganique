package com.blogspot.android_czy_java.beautytips.usecase.account.userlist

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase

class CreateUserListRequestsUseCase: NestedListRequestUseCase<UserListRecipeRequest> {
    override fun execute(): NestedListRequest<UserListRecipeRequest> {
        val requests = arrayListOf<UserListRecipeRequest>()

        requests.add(UserListRecipeRequest("Favorites", Order.POPULARITY))

        return UserListRequest(requests)
    }
}