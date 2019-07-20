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
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel

import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderFragmentViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderFragmentData
import javax.inject.Inject


class DetailHeaderFragment : DetailFragment() {


    @BindView(R.id.image)
    lateinit var image: ImageView

    @BindView(R.id.title)
    lateinit var title: TextView

    @BindView(R.id.category)
    lateinit var category: TextView

    @BindView(R.id.subcategory)
    lateinit var subcategory: TextView

    @Inject
    lateinit var viewModel: HeaderFragmentViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_detail_header, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.headerFragmentLiveData.observe(this, Observer { render(it) } )
        getRecipeId()?.let { viewModel.init(it) }
    }

    private fun render(uiModel: GenericUiModel<HeaderFragmentData>) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> prepareContent(uiModel.data)

            is GenericUiModel.LoadingError -> return //TODO: implement
        }
    }

    private fun prepareContent(data: HeaderFragmentData) {

        loadImage(data)
        title.text = data.title
        category.text = data.category
        subcategory.text = data.subcategory

    }

    private fun loadImage(data: HeaderFragmentData) {

        Glide.with(this@DetailHeaderFragment).load(data.imageUrl).into(image)
        image.contentDescription = resources.getString(R.string.description_tip_image, data.title)
    }


}
