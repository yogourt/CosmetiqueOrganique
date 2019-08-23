package com.blogspot.android_czy_java.beautytips.usecase.common

import io.reactivex.Single

interface UseCaseInterface<REQUEST, RESPONSE> {

    fun execute(request: REQUEST): Single<RESPONSE>

}