package com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail

import androidx.lifecycle.LiveData
import com.blogspot.android_czy_java.beautytips.exception.common.DataNotFoundException

interface RecipeDetailRepositoryInterface {


    @Throws(DataNotFoundException::class)
    fun getTitle(itemId: Long): String

    @Throws(DataNotFoundException::class)
    fun getImageUrl(itemId: Long): String


    @Throws(DataNotFoundException::class)
    fun getCategory(itemId: Long): String


    @Throws(DataNotFoundException::class)
    fun getSubcategory(itemId: Long): String

    @Throws(DataNotFoundException::class)
    fun getDescription(recipeId: Long): String

    fun getAuthor(recipeId: Long): String?

    fun getSource(recipeId: Long): String?

    @Throws(DataNotFoundException::class)
    fun getIngredients(recipeId: Long): List<String>

    fun getOptionalIngredientsWithQuantity(recipeId: Long): List<String>

    fun getIngredientsWithQuantity(recipeId: Long): List<String>

    fun getFavNum(recipeId: Long): LiveData<Long>

}