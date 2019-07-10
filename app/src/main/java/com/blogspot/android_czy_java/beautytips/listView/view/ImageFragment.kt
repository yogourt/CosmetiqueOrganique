package com.blogspot.android_czy_java.beautytips.listView.view


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.TabletDetailViewModel
import com.bumptech.glide.Glide

import butterknife.BindView
import butterknife.ButterKnife

import com.blogspot.android_czy_java.beautytips.viewmodel.common.ImageFragmentViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.common.ImageFragmentData
import com.blogspot.android_czy_java.beautytips.viewmodel.common.ImageFragmentUiModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ImageFragment : Fragment() {


    @BindView(R.id.image)
    lateinit var mImageView: ImageView

    @BindView(R.id.title_text_view)
    lateinit var mTitle: TextView

    @BindView(R.id.icon_back)
    lateinit var mIconBack: ImageView

    @Inject
    lateinit var detailViewModel: TabletDetailViewModel

    @Inject
    lateinit var viewModel: ImageFragmentViewModel


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_detail_image, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.imageFragmentLiveData.observe(this, Observer { render(it) } )
        viewModel.init(detailViewModel.chosenItemId)
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

        if (detailViewModel.isShowingIngredientFromRecipe) {
            prepareBackIcon()
        }

    }

    private fun loadImage(data: ImageFragmentData) {

        Glide.with(this@ImageFragment).load(data.imageUrl).into(mImageView)
        mImageView.contentDescription = resources.getString(R.string.description_tip_image, data.title)
    }

    private fun prepareBackIcon() {
        mIconBack.visibility = View.VISIBLE
        mIconBack.setOnClickListener {
            detailViewModel.onGoingBackFromIngredientToDetail()
        }
    }

}
