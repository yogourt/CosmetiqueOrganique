package com.blogspot.android_czy_java.beautytips.usecase.account.userlist

import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.UserListRecipeRequest

class UserListRequest(override val requests: List<UserListRecipeRequest>):
        NestedListRequest<UserListRecipeRequest>(requests) {

    constructor(request: UserListRecipeRequest): this(listOf(request))
}