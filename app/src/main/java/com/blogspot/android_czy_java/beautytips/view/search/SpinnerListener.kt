package com.blogspot.android_czy_java.beautytips.view.search

import android.view.View
import android.widget.AdapterView
import com.farbod.labelledspinner.LabelledSpinner

abstract class SpinnerListener : LabelledSpinner.OnItemChosenListener {

    override fun onNothingChosen(labelledSpinner: View?, adapterView: AdapterView<*>?) {
    }

    override fun onItemChosen(labelledSpinner: View?, adapterView: AdapterView<*>?, itemView: View?, position: Int, id: Long) {
        onItemChosen(adapterView, id)
    }

    abstract fun onItemChosen(adapterView: AdapterView<*>?, id: Long)
}