package com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail

import android.text.TextUtils
import androidx.lifecycle.LiveData
import com.blogspot.android_czy_java.beautytips.database.detail.DetailDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.repository.exception.DataNotFoundException

class RecipeDetailRepository(private val detailDao: DetailDao, private val recipeDao: RecipeDao):
        RecipeDetailRepositoryInterface {

    override fun getTitle(itemId: Long): String {
        val title = detailDao.getTitle(itemId)
        return checkIfValid(title)

    }

    override fun getImageUrl(itemId: Long): String {
        val imageUrl = detailDao.getImageUrl(itemId)
        return checkIfValid(imageUrl)
    }

    override fun getDescription(recipeId: Long): String {
        val description = detailDao.getDescription(recipeId)
        return checkIfValid(description)
    }

    override fun getAuthor(recipeId: Long) = detailDao.getDescription(recipeId)


    override fun getSource(recipeId: Long) = detailDao.getSource(recipeId)

    override fun getIngredients(recipeId: Long): List<String> {
        val ingredients = detailDao.getIngredients(recipeId)
        return checkIfValid(ingredients).split(", ")
    }

    override fun getFavNum(recipeId: Long) = recipeDao.getFavNum(recipeId)


    private fun checkIfValid(title: String): String {
        if (!TextUtils.isEmpty(title)) {
            return title
        } else {
            throw DataNotFoundException()
        }
    }

}