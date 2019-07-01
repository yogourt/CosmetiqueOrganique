package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel

class RecipeRepository(private val appContext: Context): RecipeRepositoryInterface {

    private val recipeDao = AppDatabase.getInstance(appContext).recipeDao()
    override var category: String = "all"

    override val recipeLiveData = MutableLiveData<List<RecipeMappedModel>>()

    override fun getByDate() {
        recipeLiveData.value = recipeDao.getAllRecipes().value
    }

    override fun getByPopularity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}