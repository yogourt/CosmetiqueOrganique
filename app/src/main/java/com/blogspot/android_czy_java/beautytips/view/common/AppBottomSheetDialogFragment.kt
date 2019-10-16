package com.blogspot.android_czy_java.beautytips.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class AppBottomSheetDialogFragment : BottomSheetDialogFragment() {

    internal fun expand() {
        dialog?.let { dialog ->
            dialog.setOnShowListener {
                val bottomSheet = (dialog as BottomSheetDialog)
                        .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.let {
                    BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    internal fun collapse() {
        val bottomSheet = (dialog as BottomSheetDialog)
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


}