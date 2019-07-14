package com.blogspot.android_czy_java.beautytips.view.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer

import com.blogspot.android_czy_java.beautytips.R
import com.bumptech.glide.Glide

import butterknife.BindView
import butterknife.ButterKnife

import com.blogspot.android_czy_java.beautytips.viewmodel.detail.tablet.ImageFragmentViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.ImageFragmentData
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.ImageFragmentUiModel
import javax.inject.Inject


class ImageFragment : DetailBaseFragment() {


    @BindView(R.id.image)
    lateinit var mImageView: ImageView

    @BindView(R.id.title_text_view)
    lateinit var mTitle: TextView

    @BindView(R.id.icon_back)
    lateinit var mIconBack: ImageView

    @Inject
    lateinit var viewModel: ImageFragmentViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_detail_image, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.imageFragmentLiveData.observe(this, Observer { render(it) } )
        viewModel.init(getRecipeId())
    }

    private fun render(model: ImageFragmentUiModel) {

        when (model) {
            is ImageFragmentUiModel.LoadingSuccess -> prepareContent(model.data)

            is ImageFragmentUiModel.LoadingError -> return //TODO: implement
        }
    }

    private fun prepareContent(data: ImageFragmentData) {

        loadImage(data)

        mTitle.text = data.title

    }

    private fun loadImage(data: ImageFragmentData) {

        Glide.with(this@ImageFragment).load(data.imageUrl).into(mImageView)
        mImageView.contentDescription = resources.getString(R.string.description_tip_image, data.title)
    }


}
