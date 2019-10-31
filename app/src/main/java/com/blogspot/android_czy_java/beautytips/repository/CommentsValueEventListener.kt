package com.blogspot.android_czy_java.beautytips.repository

import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.blogspot.android_czy_java.beautytips.database.comment.CommentListConverter
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import timber.log.Timber

class CommentsValueEventListener(database: AppDatabase) : ValueEventListener {

    private val commentDao = database.commentDao()
    private val userDao = database.userDao()

    override fun onDataChange(snapshot: DataSnapshot) {

        Thread(Runnable {

            val comments = mutableListOf<CommentModel>()

            for (recipeComments in snapshot.children) {
                val recipeId = recipeComments.key?.toLong() ?: continue
                comments.addAll(CommentListConverter(recipeComments, recipeId).getComments())
            }

            commentDao.insertComments(comments)

            fetchCommentAuthors(comments)

        }).start()
    }

    private fun fetchCommentAuthors(comments: MutableList<CommentModel>) {
        FirebaseDatabase
                .getInstance()
                .getReference(FirebaseKeys.REFERENCE_USERS)
                .addListenerForSingleValueEvent(
                        UsersValueEventListener(
                                comments.map {
                                    it.authorId
                                }
                                        .toSet(),
                                userDao)
                )
    }

    override fun onCancelled(error: DatabaseError) {
        Timber.e(error.message)
    }
}