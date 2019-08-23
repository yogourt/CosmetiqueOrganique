package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.*
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase

class CreateRecipeRequestsUseCase: NestedListRequestUseCase<RecipeRequest> {

    override fun execute(): MainListRequest {

        val requests = arrayListOf<RecipeRequest>()

        requests.add(RecipeRequest(CategoryAll.SUBCATEGORY_ALL, Order.NEW))
        requests.add(RecipeRequest(CategoryAll.SUBCATEGORY_ALL, Order.POPULARITY))
        requests.add(RecipeRequest(CategoryHair.SUBCATEGORY_ALL, Order.NEW))
        requests.add(RecipeRequest(CategoryFace.SUBCATEGORY_ALL, Order.NEW))
        requests.add(RecipeRequest(CategoryBody.SUBCATEGORY_ALL, Order.NEW))


        return MainListRequest(requests)
    }
}