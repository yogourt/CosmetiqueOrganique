package com.blogspot.android_czy_java.beautytips.view.detail

import android.content.Context
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.AppFragment
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.view.listView.view.MainActivity
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class DetailFragment: AppFragment() {

    @Inject
    lateinit var detailActivityViewModel: DetailActivityViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun getRecipeId(): Long? {
        return if(activity is MainActivity) {
            detailActivityViewModel.chosenItemId
        } else {
            val recipeId = activity?.intent?.getLongExtra(IntentDataKeys.KEY_RECIPE_ID, 0)
            if(recipeId == null || recipeId == 0L) {
                notifyAboutErrorAndFinishActivity()
                null
            } else recipeId
        }
    }

    private fun notifyAboutErrorAndFinishActivity() {
        view?.let { Snackbar.make(it, R.string.error_not_able_to_load_recipe, Snackbar.LENGTH_SHORT) }
        activity?.finish()
    }


}