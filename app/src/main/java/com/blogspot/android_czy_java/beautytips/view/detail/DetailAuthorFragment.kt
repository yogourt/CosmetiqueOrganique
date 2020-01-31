package com.blogspot.android_czy_java.beautytips.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.AuthorViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_author.*
import javax.inject.Inject

class DetailAuthorFragment : DetailFragment() {

    @Inject
    lateinit var viewModel: AuthorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_detail_author,
                container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.authorLiveData.observe(this, Observer { render(it) })
        getRecipeId()?.let { viewModel.init(it) }

    }

    private fun render(uiModel: GenericUiModel<UserModel>?) {
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                prepareContent(uiModel.data)
            }
        }
    }

    private fun prepareContent(author: UserModel) {
        photo?.let {
            Glide.with(it).load(author.photo).into(it)
        }
        nickname?.let {
            it.text = author.nickname
        }
    }

}