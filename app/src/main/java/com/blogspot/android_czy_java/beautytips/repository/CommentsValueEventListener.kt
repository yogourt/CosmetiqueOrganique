package com.blogspot.android_czy_java.beautytips.repository

import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class CommentsValueEventListener(database: AppDatabase): ValueEventListener {

    private val commentDao = database.commentDao()

    override fun onDataChange(snapshot: DataSnapshot) {

        val comments = mutableListOf<CommentModel>()

        for(recipeComments in snapshot.children) {
            val recipeId = recipeComments.key ?: continue

            for(comment in recipeComments.children) {
                val authorId = comment.child(FirebaseKeys.KEY_COMMENT_AUTHOR_ID).value?.toString() ?: continue
                val message = comment.child(FirebaseKeys.KEY_COMMENT_MESSAGE).value?.toString() ?: continue
                val responseTo = comment.child(FirebaseKeys.KEY_COMMENT_RESPONSE_TO).value?.toString()
                val firebaseId = comment.key ?: continue
                comments.add(
                        CommentModel(
                                firebaseId,
                                responseTo,
                                recipeId,
                                authorId,
                                message
                                )
                )
            }
        }
        commentDao.insertComments(comments)
    }

    override fun onCancelled(error: DatabaseError) {


    }
}