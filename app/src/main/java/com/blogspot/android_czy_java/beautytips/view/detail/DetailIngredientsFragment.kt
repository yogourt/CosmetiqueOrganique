package com.blogspot.android_czy_java.beautytips.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.IngredientsFragmentData
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.IngredientsFragmentViewModel
import timber.log.Timber
import javax.inject.Inject

class DetailIngredientsFragment : DetailFragment() {

    @BindView(R.id.ingredients)
    lateinit var ingredients: LinearLayout

    @Inject
    lateinit var viewModel: IngredientsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_ingredients, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.ingredientsFragmentLiveData.observe(this, Observer { render(it) })
        getRecipeId()?.let { viewModel.init(it) }
    }

    private fun render(uiModel: GenericUiModel<IngredientsFragmentData>) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> prepareContent(uiModel.data)

            is GenericUiModel.LoadingError -> return //TODO: implement
        }
    }

    private fun prepareContent(data: IngredientsFragmentData) {

        for(ingredient in data.ingredients) {
            val view = layoutInflater.inflate(R.layout.item_detail_ingredient, null)
            view.findViewById<TextView>(R.id.name).text = ingredient.name
            view.findViewById<TextView>(R.id.quantity).text = ingredient.quantity
            ingredients.addView(view)
        }

    }

}