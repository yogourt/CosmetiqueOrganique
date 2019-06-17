package com.blogspot.android_czy_java.beautytips.listView.view.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

public class NicknamePickerDialog extends DialogFragment {

    public interface NicknamePickerDialogListener {
        void onDialogSaveButtonClick(String nickname);
    }

    private NicknamePickerDialogListener mNicknamePickerDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);

        builder.setView(R.layout.dialog_nickname_picker);

        builder.setTitle(R.string.dialog_nickname_title);
        builder.setMessage(R.string.dialog_nickname_message);
        builder.setIcon(R.drawable.ic_soap);
        builder.setPositiveButton(R.string.dialog_button_positive_label,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mNicknamePickerDialogListener = (NicknamePickerDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NicknamePickerDialog" +
                    ".NicknamePickerDialogListener");
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        EditText nicknameEt = getDialog().findViewById(R.id.nickname_edit_text);
        String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        nicknameEt.setText(displayName);

    }

    @Override
    public void onResume() {
        super.onResume();


        final AlertDialog dialog = (AlertDialog)getDialog();


        //if this is not first login of this user, nickname saved in db is shown in dialog
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("userNicknames/" + userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String nickname = String.valueOf(dataSnapshot.getValue());
                        if(!nickname.equals("null") && !TextUtils.isEmpty(nickname)) {
                            EditText nicknameEt = getDialog().findViewById(R.id.nickname_edit_text);
                            nicknameEt.setText(nickname);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.d(databaseError.getMessage());
                    }
                });
        /*
        Prevent dialog from closing when clicking on activity in background - this is done because
        we need user to choose nickname at the beginning
        */
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        if (dialog.getWindow() != null) {
            //show soft keyboard
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        /*
        We also prevent dialog from closing when edit text is empty
         */
        dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText nicknameEt = getDialog().findViewById(R.id.nickname_edit_text);
                        String nickname = nicknameEt.getText().toString().trim();
                        Timber.d("Chosen nickname: " + nickname);
                        if(TextUtils.isEmpty(nickname)) {
                            Timber.d  ("Nickname is empty");
                            getDialog().findViewById(R.id.error_text_view).setVisibility(View.VISIBLE);
                        } else {
                            mNicknamePickerDialogListener.onDialogSaveButtonClick(nicknameEt.getText().toString());
                            dialog.dismiss();

                        }
                    }
                });

    }
}
