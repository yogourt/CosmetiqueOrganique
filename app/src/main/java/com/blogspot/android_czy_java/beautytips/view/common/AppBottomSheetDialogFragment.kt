package com.blogspot.android_czy_java.beautytips.view.common

import android.content.Context
import android.view.View
import com.blogspot.android_czy_java.beautytips.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class AppBottomSheetDialogFragment : BottomSheetDialogFragment() {

    var isTablet = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isTablet = resources.getBoolean(R.bool.is_tablet)
    }

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