package com.blogspot.android_czy_java.beautytips.newTip.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.blogspot.android_czy_java.beautytips.R;

public class ConfirmationDialog extends DialogFragment {

    public interface ConfirmationDialogListener {
        void onDialogSaveButtonClick();
    }

    private ConfirmationDialogListener mConfirmationDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);
        builder.setIcon(R.drawable.ic_soap);
        builder.setTitle(R.string.conf_dialog_title);
        builder.setMessage(R.string.conf_dialog_message);

        builder.setPositiveButton(R.string.conf_dialog_pos_button,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mConfirmationDialogListener.onDialogSaveButtonClick();
            }
        });

        builder.setNegativeButton(R.string.conf_dialog_neg_button, null);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mConfirmationDialogListener = (ConfirmationDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ConfirmationDialog" +
                    ".ConfirmationDialogListener");
        }
    }
}
