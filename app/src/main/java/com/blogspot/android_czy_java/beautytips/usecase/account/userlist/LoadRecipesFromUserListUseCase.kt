package com.blogspot.android_czy_java.beautytips.usecase.account.userlist

import com.blogspot.android_czy_java.beautytips.usecase.common.LoadNestedListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase

class LoadRecipesFromUserListUseCase(loadRecipesUseCase: LoadRecipesUseCase<UserListRecipeRequest>) :
        LoadNestedListDataUseCase<UserListRecipeRequest>(loadRecipesUseCase) {

    override fun createListTitle(recipeRequest: UserListRecipeRequest): String {
        return "My favorites"
    }
}