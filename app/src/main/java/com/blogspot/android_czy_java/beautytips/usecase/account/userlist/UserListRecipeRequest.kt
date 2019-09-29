package com.blogspot.android_czy_java.beautytips.usecase.account.userlist

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import java.io.Serializable

class UserListRecipeRequest(val userList: String, val userId: String, val order: Order) : Serializable