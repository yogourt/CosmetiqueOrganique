package com.blogspot.android_czy_java.beautytips.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.comment.CommentFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailBottomActionViewModel
import kotlinx.android.synthetic.main.fragment_detail_bottom_action.*
import javax.inject.Inject

class DetailBottomActionFragment : DetailFragment() {

    @Inject
    lateinit var viewModel: DetailBottomActionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_detail_bottom_action,
                container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.commentNumberLiveData.observe(this, Observer { render(it) })
        getRecipeId()?.let { viewModel.init(it) }

        prepareButtons()
    }

    private fun render(uiModel: GenericUiModel<Int>?) {

            when (uiModel) {
                is GenericUiModel.LoadingSuccess -> {
                    comments_label.text = getString(R.string.label_comments, uiModel.data.toString())
                }
            }
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