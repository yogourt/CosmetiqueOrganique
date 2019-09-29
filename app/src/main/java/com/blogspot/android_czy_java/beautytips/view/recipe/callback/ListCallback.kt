package com.blogspot.android_czy_java.beautytips.view.recipe.callback

interface ListCallback {

    interface InnerListCallback {
        fun onRecipeClick(recipeId: Long)
    }

    val innerListCallback: InnerListCallback

    fun onListClick(listId: Int)
}