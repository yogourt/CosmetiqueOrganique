package com.blogspot.android_czy_java.beautytips.view.recipe.callback

import com.blogspot.android_czy_java.beautytips.view.recipe.OneListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListViewModel

interface ListCallback {

    interface InnerListCallback {
        fun onRecipeClick(recipeId: Long)
    }

    val innerListCallback: InnerListCallback

    fun onListClick(listId: Int)

    interface OuterListCallback {
        fun onUserListClick(fragment: OneListFragment.OneUserRecipeListFragment)
    }
}