package com.blogspot.android_czy_java.beautytips.usecase.common

import io.reactivex.Single

interface NoRequestUseCaseInterface<RESPONSE> {

    fun execute(): Single<RESPONSE>

}