package com.blogspot.android_czy_java.beautytips.usecase.common

import io.reactivex.Single


interface NestedListRequestUseCase<RECIPE_REQUEST> {
    fun execute(): Single<NestedListRequest<RECIPE_REQUEST>>
}