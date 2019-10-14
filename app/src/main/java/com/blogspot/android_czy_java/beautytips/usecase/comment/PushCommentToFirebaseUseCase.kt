package com.blogspot.android_czy_java.beautytips.usecase.comment

import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

class PushCommentToFirebaseUseCase(private val commentRepository: CommentRepository) {

    fun execute(request: CommentModel) {

        Thread(Runnable {
            FirebaseDatabase
                    .getInstance()
                    .getReference("comments/${request.recipeId}")
                    .push()
                    .setValue(request, OnCompleteListener(request.id))

        }).start()
    }


    inner class OnCompleteListener(private val commentId: Long) :
            DatabaseReference.CompletionListener {
        override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
            if (error != null) return

            Thread(Runnable {
                ref.key?.let {
                    updateFirebaseId(it)
                }
            }).start()
        }

        private fun updateFirebaseId(firebaseId: String) {
            commentRepository.updateCommentFirebaseId(commentId, firebaseId)
        }
    }
}