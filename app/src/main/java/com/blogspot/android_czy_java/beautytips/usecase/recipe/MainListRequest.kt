package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest

class MainListRequest(override val requests: List<RecipeRequest>):
        NestedListRequest<RecipeRequest>(requests) {

    constructor(request: RecipeRequest): this(listOf(request))
}