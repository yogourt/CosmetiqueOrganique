package com.blogspot.android_czy_java.beautytips.usecase.search

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import java.io.Serializable

class SearchResultRequest(
        val category: CategoryInterface,
        val order: Order,
        val title: String,
        val author: String,
        val keywords: String
) : Serializable