package com.blogspot.android_czy_java.beautytips.usecase.account

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.account.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadNestedListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import io.reactivex.Single

class LoadRecipesFromUserListUseCase(loadRecipesUseCase: LoadRecipesUseCase<UserListRecipeRequest>) :
        LoadNestedListDataUseCase<UserListRecipeRequest>(loadRecipesUseCase) {

    override fun createListTitle(recipeRequest: UserListRecipeRequest): String {
        return "My favorites"
    }
}