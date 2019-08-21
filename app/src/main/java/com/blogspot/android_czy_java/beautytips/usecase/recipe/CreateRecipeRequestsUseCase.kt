package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryHair
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order

class CreateRecipeRequestsUseCase {

    fun execute(): MainListRequest {
        //todo: change

        val requests = arrayListOf<RecipeRequest>()

        requests.add(RecipeRequest(CategoryAll.SUBCATEGORY_ALL, Order.POPULARITY))
        requests.add(RecipeRequest(CategoryHair.SUBCATEGORY_ALL, Order.NEW))

        return MainListRequest(requests)
    }
}