package com.blogspot.android_czy_java.beautytips.view.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.view.detail.DetailActivity
import com.blogspot.android_czy_java.beautytips.view.listView.view.MainListAdapter
import com.blogspot.android_czy_java.beautytips.view.listView.view.RecipeListAdapter
import com.blogspot.android_czy_java.beautytips.view.listView.view.callback.ListCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_nested_list.*
import kotlinx.android.synthetic.main.fragment_nested_list.view.*
import javax.inject.Inject

abstract class RecipeListFragment : AppFragment(), ListCallback {

    @Inject
    lateinit var activityViewModel: DetailActivityViewModel

    internal lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_nested_list,
                container, false)

        recyclerView = view.recycler_view
        view.one_category_bottom_nav.setOnClickListener { loadInitialData() }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareViewModel(savedInstanceState == null)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    fun render(uiModel: GenericUiModel<MainListData>) {
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                prepareRecyclerView(uiModel.data)
            }
            is GenericUiModel.StatusLoading -> {
                loading_indicator.visibility = View.VISIBLE
                clearRecyclerView()
            }
            is GenericUiModel.LoadingError -> {
                showInfoAboutError(uiModel.message)
            }
        }
    }

    private fun prepareRecyclerView(recyclerViewList: MainListData) {

        if (loading_indicator.visibility == View.VISIBLE) {
            loading_indicator.visibility = View.INVISIBLE
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL, false)
            if (recyclerViewList.data.size == 1) {
                val list = recyclerViewList.data[0]
                adapter = RecipeListAdapter(this@RecipeListFragment,
                        list.data, false)
                prepareOneCategoryBottomNav(list.listTitle)
            } else {
                adapter = MainListAdapter(this@RecipeListFragment, recyclerViewList)
                hideOneCategoryBottomNav()
            }

        }
    }

    private fun clearRecyclerView() {
        recyclerView.apply {
            layoutManager = null
            adapter = null
            hideOneCategoryBottomNav()
        }
    }

    private fun prepareOneCategoryBottomNav(listTitle: String) {
        one_category_bottom_nav?.apply {
            title.text = listTitle
            visibility = View.VISIBLE
        }
    }

    private fun hideOneCategoryBottomNav() {
        one_category_bottom_nav?.apply {
            title.text = ""
            visibility = View.INVISIBLE
        }
    }

    private fun showInfoAboutError(message: String) {
        Snackbar.make(
                recyclerView,
                getString(R.string.database_error_msg, message),
                Snackbar.LENGTH_INDEFINITE
        ).setAction(
                R.string.retry
        ) { retryDataLoading() }
                .show()
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

    fun onBackPressed(): Boolean {
        return if (one_category_bottom_nav?.visibility == View.VISIBLE) {
            loadInitialData()
            true
        } else false
    }

    abstract fun retryDataLoading()

    abstract fun prepareViewModel(init: Boolean)

    private fun loadInitialData() = retryDataLoading()

}