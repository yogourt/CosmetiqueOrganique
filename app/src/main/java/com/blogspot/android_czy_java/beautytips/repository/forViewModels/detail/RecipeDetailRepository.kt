package com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail

import android.text.TextUtils
import com.blogspot.android_czy_java.beautytips.database.detail.DetailDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.exception.common.DataNotFoundException

class RecipeDetailRepository(private val detailDao: DetailDao, private val recipeDao: RecipeDao) :
        RecipeDetailRepositoryInterface {


    override fun getTitle(itemId: Long): String {
        val title = detailDao.getTitle(itemId)
        return checkIfValid(title)

    }

    override fun getImageUrl(itemId: Long): String {
        val imageUrl = detailDao.getImageUrl(itemId)
        return checkIfValid(imageUrl)
    }

    override fun getCategory(itemId: Long): String {
        val category = recipeDao.getCategory(itemId)
        return checkIfValid(category)
    }

    override fun getSubcategory(itemId: Long): String {
        val subcategory = recipeDao.getSubcategory(itemId)
        return checkIfValid(subcategory)
    }

    override fun getDescription(recipeId: Long): String {
        val description = detailDao.getDescription(recipeId)
        return checkIfValid(description)
    }

    override fun getAuthor(recipeId: Long) = detailDao.getAuthorId(recipeId)


    override fun getSource(recipeId: Long) = detailDao.getSource(recipeId)

    override fun getIngredients(recipeId: Long): List<String> {
        val ingredients = detailDao.getIngredients(recipeId)
        return checkIfValid(ingredients)
                .split(", ")
                .map {
                    it.substringBefore(":")
                            .trim()
                }
    }

    override fun getIngredientsWithQuantity(recipeId: Long): List<String> {
        val ingredients = detailDao.getIngredients(recipeId)
        return checkIfValid(ingredients)
                .split(", ")
    }

    override fun getOptionalIngredientsWithQuantity(recipeId: Long): List<String> =
            detailDao.getOptionalIngredients(recipeId).let {
                return if (TextUtils.isEmpty(it)) {
                    listOf()
                } else {
                    it.split(", ")
                }
            }

    override fun getFavNum(recipeId: Long) = recipeDao.getFavNum(recipeId)


    private fun checkIfValid(item: String): String {
        if (!TextUtils.isEmpty(item)) {
            return item
        } else {
            throw DataNotFoundException()
        }
    }

}