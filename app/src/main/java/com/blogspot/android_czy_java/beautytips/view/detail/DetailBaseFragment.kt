package com.blogspot.android_czy_java.beautytips.view.detail

import android.content.Context
import androidx.fragment.app.Fragment
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.view.listView.exception.RecipeIdNotFoundException
import com.blogspot.android_czy_java.beautytips.view.listView.view.MainActivity
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.tablet.TabletDetailViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class DetailBaseFragment: Fragment() {

    @Inject
    lateinit var tabletDetailViewModel: TabletDetailViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @Throws(RecipeIdNotFoundException::class)
    fun getRecipeId(): Long {
        return if(activity is MainActivity) {
            tabletDetailViewModel.chosenItemId
        } else {
            val recipeId = activity?.intent?.getLongExtra(IntentDataKeys.KEY_RECIPE_ID, 0)
            if(recipeId == null || recipeId == 0L) {
                throw RecipeIdNotFoundException()
            } else recipeId
        }
    }

}