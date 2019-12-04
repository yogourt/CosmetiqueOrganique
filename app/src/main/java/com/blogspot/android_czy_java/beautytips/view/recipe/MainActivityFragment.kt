package com.blogspot.android_czy_java.beautytips.view.recipe


import android.text.TextUtils
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.service.notification.NotificationService.Companion.KEY_ITEM

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
                innerListCallback.onRecipeClick(recipeId.toLong())
                activity!!.intent = null
            }
        }
    }

    override fun onListClick(listId: Int) {

        val request = recipeViewModel.getRequestForId(listId) ?: return
        val fragment = OneListFragment.OneRecipeListFragment.getInstance(request)
        fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.main_container, fragment, OneListFragment.TAG_ONE_LIST_FRAGMENT)
                ?.addToBackStack(null)
                ?.commit()
    }


}
