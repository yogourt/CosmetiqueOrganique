package com.blogspot.android_czy_java.beautytips.view.listView.view


import android.text.TextUtils
import androidx.lifecycle.Observer

import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeViewModel

import com.blogspot.android_czy_java.beautytips.view.common.NestedRecipeListFragment

import com.blogspot.android_czy_java.beautytips.view.listView.view.RecipeListAdapter.KEY_ITEM
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import javax.inject.Inject


class MainActivityFragment : NestedRecipeListFragment() {


    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    override fun prepareViewModel() {
        recipeViewModel.recipeListLiveData.observe(this, Observer { this.render(it) })
        recipeViewModel.init()
    }

    override fun retryDataLoading() {
        recipeViewModel.retry()
    }

    override fun onResume() {
        super.onResume()
        //open tip from notification
        if (activity != null && activity!!.intent != null) {
            val recipeId = activity!!.intent.getStringExtra(KEY_ITEM)
            if (!TextUtils.isEmpty(recipeId) && recipeId != null) {
                onRecipeClick(recipeId.toLong())
                activity!!.intent = null
            }
        }
    }


}
