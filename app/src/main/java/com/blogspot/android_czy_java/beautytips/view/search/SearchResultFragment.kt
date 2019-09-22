package com.blogspot.android_czy_java.beautytips.view.search

import com.blogspot.android_czy_java.beautytips.view.common.RecipeListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.search.SearchActivityViewModel
import javax.inject.Inject

class SearchResultFragment: RecipeListFragment() {

    @Inject
    lateinit var viewModel: SearchActivityViewModel

    override fun retryDataLoading() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun prepareViewModel(init: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListClick(listId: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}