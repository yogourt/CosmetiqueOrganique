package com.blogspot.android_czy_java.beautytips.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogspot.android_czy_java.beautytips.R
import kotlinx.android.synthetic.main.fragment_detail_bottom_action.*

class DetailBottomActionFragment : DetailFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_bottom_action,
                container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareButtons()
    }

    private fun prepareButtons() {
        discussion.setOnClickListener { onClickDiscussion() }
    }

    private fun onClickDiscussion() {
        //TODO: implement
    }
}