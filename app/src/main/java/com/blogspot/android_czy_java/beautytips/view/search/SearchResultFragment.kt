package com.blogspot.android_czy_java.beautytips.view.search

import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.view.common.RecipeListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.search.SearchActivityViewModel
import javax.inject.Inject

class SearchResultFragment : RecipeListFragment() {

    @Inject
    lateinit var viewModel: SearchActivityViewModel

    override fun retryDataLoading() {
    }

    override fun prepareViewModel(init: Boolean) {
        viewModel.recipeListLiveData.observe(this, Observer{ render(it) })
    }

    override fun onListClick(listId: Int) {
    }
}