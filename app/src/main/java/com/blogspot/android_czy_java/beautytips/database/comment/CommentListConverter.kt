package com.blogspot.android_czy_java.beautytips.database.comment

import com.google.firebase.database.DataSnapshot

class CommentListConverter(private val recipeDetailSnapshot: DataSnapshot, private val recipeId: Long) {

    fun getComments(): ArrayList<CommentModel> {

        val comments = ArrayList<CommentModel>()

        for (commentItem in recipeDetailSnapshot.child("comments").children) {
            val comment = CommentConverter(commentItem, recipeId).getComment()
            if(comment != null) {
                comments.add(comment)
            }
        }

        return comments
    }
}