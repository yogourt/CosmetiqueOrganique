package com.blogspot.android_czy_java.beautytips.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.blogspot.android_czy_java.beautytips.R

class DetailBottomActionFragment:DetailFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_bottom_action,
                container, false)
        ButterKnife.bind(this, view)
        return view
    }
}