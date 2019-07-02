package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe

import androidx.lifecycle.LiveData
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel

interface RecipeRepositoryInterface {

    val recipeLiveData: LiveData<List<RecipeMappedModel>>
    var category: CategoryInterface

    fun getByDate()
    fun getByPopularity()
}