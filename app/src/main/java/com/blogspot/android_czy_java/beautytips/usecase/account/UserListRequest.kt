package com.blogspot.android_czy_java.beautytips.usecase.account

import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest

class UserListRequest(override val requests: List<UserListRecipeRequest>):
        NestedListRequest<UserListRecipeRequest>(requests)