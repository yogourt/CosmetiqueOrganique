package com.blogspot.android_czy_java.beautytips.usecase.recipe

import android.os.Parcelable
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import java.io.Serializable

class RecipeRequest(val category: CategoryInterface, val order: Order): Serializable