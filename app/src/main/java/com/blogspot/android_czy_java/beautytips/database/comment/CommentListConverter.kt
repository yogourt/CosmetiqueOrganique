package com.blogspot.android_czy_java.beautytips.database.comment

import com.google.firebase.database.DataSnapshot

class CommentListConverter(private val recipeCommentsSnapshot: DataSnapshot,
                           private val recipeId: Long,
                           private val replyTo: String? = null) {

    fun getComments(): ArrayList<CommentModel> {

        val comments = ArrayList<CommentModel>()

        for (commentSnapshot in recipeCommentsSnapshot.children) {
            val comment = getComment(commentSnapshot)

            if (comment != null) {
                comments.add(comment)

                if (replyTo == null) {
                    comments.addAll(
                            getSubcomments(
                                    commentSnapshot.child("subcomments"),
                                    comment.id))
                }
            }
        }

        return comments
    }

    private fun getComment(commentSnapshot: DataSnapshot): CommentModel? {
        return CommentConverter(
                commentSnapshot,
                recipeId,
                replyTo)
                .getComment()
    }

    private fun getSubcomments(subcommentsSnapshot: DataSnapshot, replyTo: String): ArrayList<CommentModel> {
        return CommentListConverter(
                subcommentsSnapshot,
                recipeId,
                replyTo
        )
                .getComments()
    }


}