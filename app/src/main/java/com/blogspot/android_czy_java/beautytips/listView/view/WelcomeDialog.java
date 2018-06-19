package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.blogspot.android_czy_java.beautytips.R;

public class WelcomeDialog extends DialogFragment {

    public interface WelcomeDialogListener {
        void onPositiveButtonClicked();

        void onNegativeButtonClicked();
    }

    private WelcomeDialogListener mDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);

        builder.setTitle(R.string.welcome_dialog_title);
        builder.setMessage(R.string.welcome_dialog_message);
        builder.setIcon(R.drawable.ic_soap);
        builder.setPositiveButton(R.string.welcome_dialog_pos_button_label,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialogListener.onPositiveButtonClicked();
                    }
                });

        builder.setNegativeButton(R.string.welcome_dialog_neg_button_label,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialogListener.onNegativeButtonClicked();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mDialogListener = (WelcomeDialog.WelcomeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement WelcomeDialog" +
                    ".WelcomeDialogListener");
        }
    }

}
