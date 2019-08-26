package com.blogspot.android_czy_java.beautytips.view.account

import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.view.common.NestedRecipeListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.UserListViewModel
import javax.inject.Inject

class AccountActivityFragment: NestedRecipeListFragment() {


    @Inject
    lateinit var viewModel: UserListViewModel

    override fun retryDataLoading() {
        viewModel.retry()
    }

    override fun prepareViewModel() {
        viewModel.recipeListLiveData.observe(this, Observer { this.render(it) })
        viewModel.init()
    }










}