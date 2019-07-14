package com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail

import androidx.lifecycle.LiveData
import com.blogspot.android_czy_java.beautytips.repository.DetailRepositoryInterface
import com.blogspot.android_czy_java.beautytips.repository.exception.DataNotFoundException

interface RecipeDetailRepositoryInterface : DetailRepositoryInterface {

    @Throws(DataNotFoundException::class)
    fun getDescription(recipeId: Long): String

    fun getAuthor(recipeId: Long): String?

    fun getSource(recipeId: Long): String?

    @Throws(DataNotFoundException::class)
    fun getIngredients(recipeId: Long): List<String>

    fun getFavNum(recipeId: Long): LiveData<Long>
}