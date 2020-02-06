package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.RecipeRequest

class MainListRequest(override val requests: List<RecipeRequest>):
        NestedListRequest<RecipeRequest>(requests)