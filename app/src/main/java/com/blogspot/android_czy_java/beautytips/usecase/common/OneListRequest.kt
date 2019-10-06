package com.blogspot.android_czy_java.beautytips.usecase.common

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import java.io.Serializable

abstract class OneListRequest(open val category: CategoryInterface, open val order: Order): Serializable