package com.blogspot.android_czy_java.beautytips.usecase.comment

import android.database.sqlite.SQLiteConstraintException
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

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
            if (error != null) {
                Timber.e("Comment push error: ${error.message}")
                return
            }

            Timber.d("created ref: ${ref.key}")

            Thread(Runnable {
                ref.key?.let {
                    updateFirebaseId(it)
                }
            }).start()
        }

        private fun updateFirebaseId(firebaseId: String) {
            try {
                commentRepository.updateCommentFirebaseId(commentId, firebaseId)
            } catch (e: SQLiteConstraintException) {
                Timber.e("An error during firebase push: " +
                        "firebaseId $firebaseId already exists and cannot " +
                        "be set for comment $commentId")
            }


        }
    }
}