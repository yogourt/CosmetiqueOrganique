package com.blogspot.android_czy_java.beautytips.view.account

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.view.common.RecipeListFragment
import com.blogspot.android_czy_java.beautytips.view.recipe.OneListFragment
import com.blogspot.android_czy_java.beautytips.view.recipe.callback.ListCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.account.UserListViewModel
import kotlinx.android.synthetic.main.fragment_nested_list.*
import javax.inject.Inject

class UserListFragment : RecipeListFragment() {

    @Inject
    lateinit var viewModel: UserListViewModel

    @Inject
    lateinit var accountViewModel: AccountViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        accountViewModel.userLiveData.observe(this, Observer { handleUserChange(it) })
    }

    override fun onListClick(listId: Int) {
        val request = viewModel.getRequestForId(listId) ?: return
        val fragment = OneListFragment.OneUserRecipeListFragment.getInstance(request)

        activity?.let {
            (it as ListCallback.OuterListCallback).onUserListClick(fragment)
        }
    }

    override fun retryDataLoading() {
        viewModel.retry()
    }

    override fun prepareViewModel(init: Boolean) {
        viewModel.recipeListLiveData.observe(this, Observer { this.render(it) })
        if (init) viewModel.init()
    }

    private fun handleUserChange(uiModel: GenericUiModel<UserModel>?) {

        recycler_view?.visibility = if (uiModel is GenericUiModel.LoadingSuccess) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> retryDataLoading()
        }
    }

}