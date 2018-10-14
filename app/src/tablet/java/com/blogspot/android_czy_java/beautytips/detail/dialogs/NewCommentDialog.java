package com.blogspot.android_czy_java.beautytips.detail.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.firebase.CommentsFirebaseHelper;

public class NewCommentDialog extends DialogFragment {

    private String comment;
    private String recipeId;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);

        builder.setMessage(R.string.comment_dialog_msg);
        builder.setTitle(R.string.comment_dialog_title);

        builder.setPositiveButton(R.string.comment_dialog_positive_button,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CommentsFirebaseHelper.saveComment(recipeId, comment);
            }
        });

        builder.setNegativeButton(R.string.comment_dialog_negative_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        return builder.create();
    }

    public void setComment(String comment, String recipeId) {
        this.comment = comment;
        this.recipeId = recipeId;
    }
}
