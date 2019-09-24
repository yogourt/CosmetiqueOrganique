package com.blogspot.android_czy_java.beautytips.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.common.AppFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import com.blogspot.android_czy_java.beautytips.viewmodel.search.SearchActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_activity_search.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import javax.inject.Inject

class SearchActivityFragment : AppFragment() {

    @Inject
    lateinit var viewModel: SearchActivityViewModel

    private lateinit var searchSheetBehaviour: BottomSheetBehavior<FrameLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_activity_search, container, false)
        searchSheetBehaviour = BottomSheetBehavior.from(view.fragment_search_container)
        searchSheetBehaviour.apply {
            peekHeight = resources.getDimensionPixelSize(R.dimen.search_layout_collapsed_height)
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        viewModel.recipeListLiveData.observe(this, Observer { render(it) })
        return view;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun render(uiModel: GenericUiModel<MainListData>) {
        when(uiModel) {
            is GenericUiModel.LoadingSuccess ->
                searchSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}