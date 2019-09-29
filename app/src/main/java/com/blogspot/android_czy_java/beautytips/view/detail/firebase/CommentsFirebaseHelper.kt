package com.blogspot.android_czy_java.beautytips.view.detail.firebase

import com.blogspot.android_czy_java.beautytips.view.detail.dialogs.NewCommentDialog
import com.blogspot.android_czy_java.beautytips.view.detail.model.Comment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.Calendar

import timber.log.Timber

class CommentsFirebaseHelper(private val addCommentListener: NewCommentDialog.AddCommentListener) {

    fun saveComment(recipeId: String, commentDesc: String, commentsNum: String) {

        val authorId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseDatabase.getInstance().getReference("userNicknames/$authorId").
                addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val authorNickname = dataSnapshot.value.toString()

                val timestamp = Calendar.getInstance().timeInMillis.toString()

                val comment = Comment(authorNickname, authorId, commentDesc, false)

                FirebaseDatabase.getInstance().getReference(
                        "tips/$recipeId/comments/$timestamp").setValue(comment).addOnCompleteListener {
                    incrementCommentsNum(recipeId, commentsNum)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    private fun incrementCommentsNum(recipeId: String, commentsNum: String) {
        try {
            var commentsNumInt = commentsNum.toInt()
            commentsNumInt++
            FirebaseDatabase.getInstance().getReference("tips/$recipeId/commentsNum")
                    .setValue(commentsNumInt).addOnCompleteListener {
                        addCommentListener.onNewCommentAdded()
                    }
        } catch (exception: NumberFormatException) {
            Timber.e("exception in parsing commentsNum to int")
        }

    }
}
