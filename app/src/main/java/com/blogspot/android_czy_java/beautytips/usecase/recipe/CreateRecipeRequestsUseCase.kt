package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.*
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase
import io.reactivex.Single

class CreateRecipeRequestsUseCase : NestedListRequestUseCase<RecipeRequest> {

    override fun execute(): Single<NestedListRequest<RecipeRequest>> =
            Single.create {
                val requests = MainListRequest(arrayListOf(
                        (RecipeRequest(CategoryAll.SUBCATEGORY_ALL, Order.NEW)),
                        (RecipeRequest(CategoryAll.SUBCATEGORY_ALL, Order.POPULARITY)),
                        (RecipeRequest(CategoryHair.SUBCATEGORY_ALL, Order.NEW)),
                        (RecipeRequest(CategoryFace.SUBCATEGORY_ALL, Order.NEW)),
                        (RecipeRequest(CategoryBody.SUBCATEGORY_ALL, Order.NEW))
                ))

                it.onSuccess(requests)
            }

}