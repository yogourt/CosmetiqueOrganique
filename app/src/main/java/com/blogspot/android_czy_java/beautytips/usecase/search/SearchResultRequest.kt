package com.blogspot.android_czy_java.beautytips.usecase.search

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order

class SearchResultRequest(
        val category: CategoryInterface,
        val order: Order,
        val title: String? = null,
        val author: String? = null,
        val keywords: String? = null
)