package com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment

import com.blogspot.android_czy_java.beautytips.database.detail.DetailDao

class CommentRepository(private val recipeDetailDao: DetailDao) {

    fun getCommentsForRecipe(recipeId: Long) = recipeDetailDao.getComments(recipeId)

}