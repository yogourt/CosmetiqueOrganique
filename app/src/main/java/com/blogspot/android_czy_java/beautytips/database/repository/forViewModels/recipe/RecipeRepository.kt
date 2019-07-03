package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe

import androidx.lifecycle.MutableLiveData
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel


class RecipeRepository(private val recipeDao: RecipeDao) : RecipeRepositoryInterface {

    override var category: CategoryInterface = CategoryAll.SUBCATEGORY_ALL


    override val recipeLiveData = MutableLiveData<List<RecipeMappedModel>>()

    override fun getByDate() {

        if(category == CategoryAll.SUBCATEGORY_ALL) {
            recipeLiveData.value = recipeDao.getAllRecipes().value
        } else if(category.isSubcategoryAll()) {
            recipeLiveData.value = recipeDao.getRecipesByCategory(category.getCategoryLabel())
        }
    }

    override fun getByPopularity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}