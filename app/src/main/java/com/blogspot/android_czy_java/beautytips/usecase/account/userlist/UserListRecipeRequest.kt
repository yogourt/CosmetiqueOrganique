package com.blogspot.android_czy_java.beautytips.usecase.account.userlist

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.common.OneListRequest

class UserListRecipeRequest(val userList: String, val userId: String, override val order: Order)
    : OneListRequest(CategoryAll.SUBCATEGORY_ALL, order)