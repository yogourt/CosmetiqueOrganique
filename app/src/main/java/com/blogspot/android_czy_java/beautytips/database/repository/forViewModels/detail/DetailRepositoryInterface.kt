package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.detail

import android.net.Uri
import androidx.lifecycle.LiveData

interface DetailRepositoryInterface {

    fun getTitle(): String
    fun getImage(): Uri
    fun getDescription(): String
    fun getAuthor(): String
    fun getIngredients(): List<String>
    fun getFavNum(): LiveData<Long>

}