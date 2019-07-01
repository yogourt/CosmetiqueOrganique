package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels

import androidx.lifecycle.LiveData
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel

interface RecipeRepositoryInterface {

    val recipeLiveData: LiveData<List<RecipeMappedModel>>
    var category: String

    fun getByDate()
    fun getByPopularity()
}