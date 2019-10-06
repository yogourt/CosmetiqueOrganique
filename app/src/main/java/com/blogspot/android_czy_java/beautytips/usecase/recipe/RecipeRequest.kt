package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.common.OneListRequest

class RecipeRequest(override val category: CategoryInterface, override val order: Order):
        OneListRequest(category, order)