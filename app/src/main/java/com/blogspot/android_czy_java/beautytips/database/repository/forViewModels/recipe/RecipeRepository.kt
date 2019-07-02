package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel

class RecipeRepository(appContext: Context): RecipeRepositoryInterface {

    private val recipeDao = AppDatabase.getInstance(appContext).recipeDao()
    override var category: CategoryInterface = CategoryAll.Subcategory.SUBCATEGORY_ALL


    override val recipeLiveData = MutableLiveData<List<RecipeMappedModel>>()

    override fun getByDate() {
        recipeLiveData.value = recipeDao.getAllRecipes().value
    }

    override fun getByPopularity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}