package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadNestedListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadRecipesUseCase

open class LoadListDataUseCase(loadRecipesUseCase: LoadRecipesUseCase<RecipeRequest>,
                               recipeRepositoryInterface: RecipeRepositoryInterface<RecipeRequest>):
        LoadNestedListDataUseCase<RecipeRequest>(loadRecipesUseCase, recipeRepositoryInterface)
