package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.blogspot.android_czy_java.beautytips.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class NicknamePickerDialog extends DialogFragment {

    public interface DialogListener {
        void onDialogSaveButtonClick(String nickname);
    }

    private DialogListener mDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);


        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_nickname_picker, null);
        builder.setView(R.layout.dialog_nickname_picker);

        builder.setPositiveButton(R.string.dialog_button_positive_label,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText nicknameEt = getDialog().findViewById(R.id.nickname_edit_text);
                mDialogListener.onDialogSaveButtonClick(nicknameEt.getText().toString());
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mDialogListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NicknamePickerDialog" +
                    ".DialogLister");
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        EditText nicknameEt = getDialog().findViewById(R.id.nickname_edit_text);
        String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        nicknameEt.setText(displayName);

    }

}
