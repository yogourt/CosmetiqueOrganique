package com.blogspot.android_czy_java.beautytips.view.listView.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.blogspot.android_czy_java.beautytips.R

class SupportPromptDialog : DialogFragment() {

    companion object {
        const val DIALOG_TAG = "Support Prompt Dialog"
    }


    lateinit var buttonCallback: SupportPromptDialogInterface


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.DialogStyle)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_support_prompt, null)

            builder.setView(view)
                    .setIcon(R.drawable.withoutback)
                    .setTitle(R.string.dialog_support_prompt_title)
                    .setPositiveButton(R.string.dialog_support_prompt_button_watch_add
                    ) { _, _ -> buttonCallback.onWatchAddButtonClicked() }
                    .setNegativeButton(R.string.dialog_support_prompt_button_write
                    ) { _, _ -> buttonCallback.onWriteButtonClicked() }
                    .setNeutralButton(R.string.dialog_support_prompt_close
                    ) { _, _ -> dialog?.cancel() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            buttonCallback = context as SupportPromptDialogInterface
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement SupportPromptDialogInterface"))
        }
    }

    override fun onResume() {
        super.onResume()


        val dialog = dialog as AlertDialog

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

}