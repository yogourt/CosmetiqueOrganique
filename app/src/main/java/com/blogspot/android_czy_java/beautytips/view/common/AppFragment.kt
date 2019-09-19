package com.blogspot.android_czy_java.beautytips.view.common

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.blogspot.android_czy_java.beautytips.R

open class AppFragment: Fragment() {

    var isTablet = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isTablet = resources.getBoolean(R.bool.is_tablet)
    }

}