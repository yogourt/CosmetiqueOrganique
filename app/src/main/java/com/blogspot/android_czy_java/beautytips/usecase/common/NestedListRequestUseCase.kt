package com.blogspot.android_czy_java.beautytips.usecase.common

import com.blogspot.android_czy_java.beautytips.usecase.recipe.MainListRequest

interface NestedListRequestUseCase<RECIPE_REQUEST> {
    fun execute(): NestedListRequest<RECIPE_REQUEST>
}