package com.blogspot.android_czy_java.beautytips.viewmodel.recipe

import com.blogspot.android_czy_java.beautytips.usecase.common.RecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.recipe.CreateRecipeRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadListDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.common.NestedRecipeListViewModel


class RecipeViewModel(createRecipeRequestsUseCase: CreateRecipeRequestsUseCase,
                      loadListDataUseCase: LoadListDataUseCase
) : NestedRecipeListViewModel<RecipeRequest>(
        createRecipeRequestsUseCase,
        loadListDataUseCase
)
