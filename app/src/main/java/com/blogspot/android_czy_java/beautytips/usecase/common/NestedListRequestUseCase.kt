package com.blogspot.android_czy_java.beautytips.usecase.common


interface NestedListRequestUseCase<RECIPE_REQUEST> {
    fun execute(): NestedListRequest<RECIPE_REQUEST>
    fun getOneRequest(listId: Int): RECIPE_REQUEST
}