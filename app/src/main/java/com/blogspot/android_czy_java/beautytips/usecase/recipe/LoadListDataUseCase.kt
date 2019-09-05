package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadNestedListDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.InnerListData
import io.reactivex.Observable

open class LoadListDataUseCase(loadRecipesUseCase: LoadRecipesUseCase<RecipeRequest>,
                               recipeRepositoryInterface: RecipeRepositoryInterface<RecipeRequest>):
        LoadNestedListDataUseCase<RecipeRequest>(loadRecipesUseCase, recipeRepositoryInterface) {

    override fun createListTitle(recipeRequest: RecipeRequest): String {
        recipeRequest.apply {
            return when (category) {
                is CategoryAll -> order.label
                else -> {
                    if (category.isSubcategoryAll()) {
                        category.getCategoryLabel()
                    } else {
                        category.getSubcategoryLabel()
                    }
                }
            }
        }
    }

}