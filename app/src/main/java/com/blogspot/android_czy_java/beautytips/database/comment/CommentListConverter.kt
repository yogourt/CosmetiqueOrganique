package com.blogspot.android_czy_java.beautytips.database.comment

import com.google.firebase.database.DataSnapshot

class CommentListConverter(private val recipeCommentsSnapshot: DataSnapshot,
                           private val recipeId: Long) {

    fun getComments(): ArrayList<CommentModel> {

        val comments = ArrayList<CommentModel>()

        for (commentSnapshot in recipeCommentsSnapshot.children) {
            val comment = getComment(commentSnapshot)

            if (comment != null) {
                comments.add(comment)
            }
        }
        return comments
    }

    private fun getComment(commentSnapshot: DataSnapshot): CommentModel? {
        return CommentConverter(
                commentSnapshot,
                recipeId)
                .getComment()
    }

}