package com.blogspot.android_czy_java.beautytips.database.comment

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.database.DataSnapshot

class CommentConverter(private val commentSnapshot: DataSnapshot,
                       private val recipeId: Long) {

    fun getComment(): CommentModel? {
        val id = commentSnapshot.key ?: return null
        val replyTo = commentSnapshot.child(FirebaseKeys.KEY_COMMENT_RESPONSE_TO).value?.toString()
        val author = commentSnapshot.child(FirebaseKeys.KEY_COMMENT_AUTHOR).value.toString()
        val authorId = commentSnapshot.child(FirebaseKeys.KEY_COMMENT_AUTHOR_ID).value.toString()
        val message = commentSnapshot.child(FirebaseKeys.KEY_COMMENT_MESSAGE).value.toString()
        return CommentModel(id, replyTo, recipeId, authorId, author, message)
    }
}