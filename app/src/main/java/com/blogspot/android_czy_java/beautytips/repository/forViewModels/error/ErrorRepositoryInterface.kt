package com.blogspot.android_czy_java.beautytips.repository.forViewModels.error

import androidx.lifecycle.LiveData

interface ErrorRepositoryInterface {

    fun isError(): LiveData<Boolean>

}