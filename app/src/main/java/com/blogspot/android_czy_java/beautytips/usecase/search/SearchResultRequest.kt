package com.blogspot.android_czy_java.beautytips.usecase.search

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.common.OneListRequest

class SearchResultRequest(
        override val category: CategoryInterface,
        override val order: Order,
        val title: String,
        val author: String,
        val keywords: String
) : OneListRequest(category, order)