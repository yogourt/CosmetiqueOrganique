package com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment

import com.blogspot.android_czy_java.beautytips.database.comment.CommentDao
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel

class CommentRepository(private val commentDao: CommentDao) {

    fun getCommentsForRecipe(recipeId: Long) = commentDao.getComments(recipeId)

    fun getCommentNumberForRecipe(recipeId: Long) = commentDao.getCommentNumberForRecipe(recipeId)

    fun addComment(comment: CommentModel) = commentDao.insertComment(comment)

    fun updateCommentFirebaseId(commentId: Long, firebaseId: String) =
            commentDao.updateCommentFirebaseId(commentId, firebaseId)

}