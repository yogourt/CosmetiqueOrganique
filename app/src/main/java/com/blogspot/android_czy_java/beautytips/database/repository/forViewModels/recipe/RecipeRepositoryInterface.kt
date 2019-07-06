package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe

import androidx.lifecycle.LiveData
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel
import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel
import io.reactivex.Single

interface RecipeRepositoryInterface {

    fun getByDate(category: CategoryInterface): Single<List<RecipeMappedModel>>
    fun getByPopularity(category: CategoryInterface): Single<List<RecipeMappedModel>>
}