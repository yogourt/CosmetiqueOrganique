package com.blogspot.android_czy_java.beautytips.listView.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel
import com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe.RecipeRepositoryInterface

class RecipeViewModel(private val repository: RecipeRepositoryInterface):
        ViewModel() {


    val recipeLiveData: LiveData<List<RecipeMappedModel>> =  repository.recipeLiveData

    var category: String = "all"
    set (newCategory) {
        repository.category = newCategory
        field = newCategory
    }

    fun getRecipesByDate()  {
        repository.getByDate()
    }

    fun getRecipesByPopularity() {
        repository.getByPopularity()
    }

}