package com.blogspot.android_czy_java.beautytips.view.listView.view


import android.text.TextUtils
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.notifications.NotificationService.Companion.KEY_ITEM

import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeViewModel

import com.blogspot.android_czy_java.beautytips.view.common.RecipeListFragment

import javax.inject.Inject


class MainActivityFragment : RecipeListFragment() {

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    override fun prepareViewModel(init: Boolean) {
        recipeViewModel.recipeListLiveData.observe(this, Observer { this.render(it) })
        if (init) recipeViewModel.init()
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

    override fun onListClick(listId: Int) {
        recipeViewModel.loadOneList(listId)
    }


}
