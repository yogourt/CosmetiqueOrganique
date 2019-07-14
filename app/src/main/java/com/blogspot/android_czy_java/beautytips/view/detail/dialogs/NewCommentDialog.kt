package com.blogspot.android_czy_java.beautytips.view.detail.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.detail.firebase.CommentsFirebaseHelper

class NewCommentDialog : DialogFragment() {

    private var comment = ""
    private var recipeId = ""
    private var commentsNum = ""

    private lateinit var mAddCommentListener: AddCommentListener

    interface AddCommentListener {
        fun onNewCommentAdded()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity, R.style.DialogStyle)

        builder.setTitle(R.string.comment_dialog_title)
        builder.setMessage(R.string.comment_dialog_msg)

        builder.setPositiveButton(R.string.comment_dialog_positive_button
        ) { _, _ ->
            CommentsFirebaseHelper(mAddCommentListener).saveComment(recipeId, comment, commentsNum)
        }

        builder.setNegativeButton(R.string.comment_dialog_negative_button
        ) { _, _ -> }

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mAddCommentListener = context as AddCommentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement NewCommentDialog." +
                    "AddCommentListener")
        }

    }

    fun setComment(comment: String, recipeId: String, commentsNum: String) {
        this.comment = comment
        this.recipeId = recipeId
        this.commentsNum = commentsNum
    }
}
