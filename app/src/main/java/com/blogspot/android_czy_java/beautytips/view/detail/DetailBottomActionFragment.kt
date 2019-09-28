package com.blogspot.android_czy_java.beautytips.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.comment.CommentFragment
import kotlinx.android.synthetic.main.fragment_detail_bottom_action.*

class DetailBottomActionFragment : DetailFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_detail_bottom_action,
                container, false)

        return view;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareButtons()
    }

    private fun prepareButtons() {
        comments.setOnClickListener { onClickComments() }
    }

    private fun onClickComments() {
        getRecipeId()?.let {
            val commentFragment = CommentFragment.getInstance(it)
            fragmentManager?.let {
                commentFragment.show(it, CommentFragment.TAG_COMMENTS_FRAGMENT)
            }
        }
    }
}