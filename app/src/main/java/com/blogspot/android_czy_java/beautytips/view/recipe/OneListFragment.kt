package com.blogspot.android_czy_java.beautytips.view.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.view.common.AppFragment
import com.blogspot.android_czy_java.beautytips.view.detail.DetailActivity
import com.blogspot.android_czy_java.beautytips.view.recipe.callback.ListCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_one_list.*
import kotlinx.android.synthetic.main.fragment_one_list.view.*
import javax.inject.Inject

sealed class OneListFragment<RECIPE_REQUEST, VIEW_MODEL : OneListViewModel<RECIPE_REQUEST>> :
        AppFragment(), ListCallback.InnerListCallback {

    @Inject
    lateinit var activityViewModel: DetailActivityViewModel

    @Inject
    lateinit var viewModel: VIEW_MODEL


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_one_list, container, false)
        prepareRecipeList(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareViewModel()
    }

    private fun prepareRecipeList(view: View) {
        val recipeList = view.recipe_list
        val params = FrameLayout.LayoutParams(recipeList.layoutParams)
        if (context is MainActivity) {
            params.bottomMargin = resources.getDimensionPixelSize(R.dimen.main_list_bottom_padding)
        } else if (context is DetailActivity) {
            params.topMargin = resources.getDimensionPixelSize(R.dimen.main_list_bottom_padding)
        }
        recipeList.layoutParams = params
    }

    private fun prepareViewModel() {
        viewModel.recipeListLiveData.observe(this, Observer { render(it) })
        arguments?.getSerializable(KEY_REQUEST)?.let {
            viewModel.getList(it as RECIPE_REQUEST)
        }
    }

    private fun render(uiModel: GenericUiModel<List<RecipeModel>>?) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                recipe_list.apply {
                    adapter = RecipeListAdapter(this@OneListFragment,
                            uiModel.data, false)
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }
            }

            is GenericUiModel.StatusLoading -> {
            }
            is GenericUiModel.LoadingError -> {

            }
        }
    }

    override fun onRecipeClick(recipeId: Long) {
        if (isTablet) {
            activityViewModel.chosenItemId = recipeId
        } else {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IntentDataKeys.KEY_RECIPE_ID, recipeId)
            startActivity(intent)
        }
    }


    companion object {
        const val KEY_REQUEST = "recipe request"
        const val TAG_ONE_LIST_FRAGMENT = "one list fragment"
    }


    class OneRecipeListFragment : OneListFragment<RecipeRequest,
            OneListViewModel.OneRecipeListViewModel>() {
        companion object {
            fun getInstance(request: RecipeRequest): OneRecipeListFragment {
                val fragment = OneRecipeListFragment()
                val bundle = Bundle()
                bundle.putSerializable(KEY_REQUEST, request)
                fragment.arguments = bundle
                return fragment
            }
        }
    }

    class OneUserRecipeListFragment : OneListFragment<UserListRecipeRequest,
            OneListViewModel.OneUserRecipeListViewModel>() {
        companion object {
            fun getInstance(request: UserListRecipeRequest): OneUserRecipeListFragment {
                val fragment = OneUserRecipeListFragment()
                val bundle = Bundle()
                bundle.putSerializable(KEY_REQUEST, request)
                fragment.arguments = bundle
                return fragment
            }
        }
    }

    class OneSearchRecipeListFragment : OneListFragment<SearchResultRequest,
            OneListViewModel.SearchResultViewModel>() {
        companion object {
            fun getInstance(request: SearchResultRequest): OneSearchRecipeListFragment {
                val fragment = OneSearchRecipeListFragment()
                val bundle = Bundle()
                bundle.putSerializable(KEY_REQUEST, request)
                fragment.arguments = bundle
                return fragment
            }
        }
    }
}