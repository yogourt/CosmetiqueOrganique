package com.blogspot.android_czy_java.beautytips.viewmodel.recipe

import com.blogspot.android_czy_java.beautytips.livedata.common.NetworkNeededNotAvailableLiveData
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.CreateRecipeRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.common.NestedRecipeListViewModel


class RecipeViewModel(createRecipeRequestsUseCase: CreateRecipeRequestsUseCase,
                      loadListDataUseCase: LoadListDataUseCase,
                      networkNeededNotAvailableLiveData: NetworkNeededNotAvailableLiveData,
                      loginUseCase: LoginUseCase
) : NestedRecipeListViewModel<RecipeRequest>(
        createRecipeRequestsUseCase,
        loadListDataUseCase,
        networkNeededNotAvailableLiveData,
        loginUseCase
)
