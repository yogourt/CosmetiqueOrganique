package com.blogspot.android_czy_java.beautytips.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.android_czy_java.beautytips.R

class DetailBottomActionFragment : DetailFragment() {

    @BindView(R.id.discussion)
    lateinit var discussion: Button

    @BindView(R.id.go_to_recipe)
    lateinit var goToRecipe: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_bottom_action,
                container, false)
        ButterKnife.bind(this, view)

        prepareButtons()

        return view
    }

    private fun prepareButtons() {
        discussion.setOnClickListener { onClickDiscussion() }
        goToRecipe.setOnClickListener { onClickGoToRecipe() }
    }

    private fun onClickDiscussion() {
        //TODO: implement
    }

    private fun onClickGoToRecipe() {

    }
}