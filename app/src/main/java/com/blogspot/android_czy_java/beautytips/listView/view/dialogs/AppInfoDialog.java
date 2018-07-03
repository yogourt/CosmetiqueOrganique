package com.blogspot.android_czy_java.beautytips.listView.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.blogspot.android_czy_java.beautytips.R;

public class AppInfoDialog extends DialogFragment {

    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);
            builder.setView(R.layout.dialog_app_info);
            builder.setIcon(R.drawable.ic_soap);
            builder.setTitle(R.string.info_dialog_title);

            builder.setPositiveButton(R.string.info_dialog_positive_button, null);
            return builder.create();
        }

    @Override
    public void onResume() {
        super.onResume();

        ((TextView)getDialog().findViewById(R.id.info_dialog_rate))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }
}

