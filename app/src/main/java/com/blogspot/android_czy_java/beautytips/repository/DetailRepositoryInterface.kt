package com.blogspot.android_czy_java.beautytips.repository

import androidx.lifecycle.LiveData
import com.blogspot.android_czy_java.beautytips.repository.exception.DataNotFoundException

interface DetailRepositoryInterface {

    @Throws(DataNotFoundException::class)
    fun getTitle(itemId: Long): String

    @Throws(DataNotFoundException::class)
    fun getImageUrl(itemId: Long): String

}