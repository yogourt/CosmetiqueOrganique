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
import com.blogspot.android_czy_java.beautytips.view.listView.view.callback.NestedListCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_nested_list.*
import javax.inject.Inject

abstract class NestedRecipeListFragment : AppFragment(), NestedListCallback {

    @Inject
    lateinit var activityViewModel: DetailActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nested_list,
                container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareViewModel()

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

        recycler_view.apply {
            if (adapter == null) {
                layoutManager = LinearLayoutManager(context,
                        RecyclerView.VERTICAL, false)
                adapter = MainListAdapter(this@NestedRecipeListFragment, recyclerViewList)
            } else {
                (adapter as MainListAdapter).apply {
                    parents = recyclerViewList
                    notifyItemInserted(recyclerViewList.data.size - 1)
                }
            }
        }
    }

    private fun showInfoAboutError(message: String) {
        Snackbar.make(
                recycler_view,
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

    abstract fun retryDataLoading()

    abstract fun prepareViewModel()

}