package com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment

import com.blogspot.android_czy_java.beautytips.database.comment.CommentDao
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel

class CommentRepository(private val commentDao: CommentDao) {

    fun getCommentsForRecipe(recipeId: Long) = commentDao.getComments(recipeId)

    fun getCommentNumberForRecipe(recipeId: Long) = commentDao.getCommentNumberForRecipe(recipeId)

    fun addComment(comment: CommentModel) = commentDao.insertComment(comment)

    fun updateCommentFirebaseId(commentId: Long, firebaseId: String) {
        val comment = commentDao.getCommentById(commentId) ?: return
        comment.firebaseId = firebaseId
        commentDao.insertComment(comment)
    }

    fun getCommentsToPush() = commentDao.getCommentsWithNullFirebaseId()

}