package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.UseCaseInterface
import io.reactivex.Single

class LoadRecipesFromUserListUseCase(private val repository: RecipeRepositoryInterface<UserListRecipeRequest>) :
        UseCaseInterface<UserListRecipeRequest, List<RecipeModel>> {

    override fun execute(request: UserListRecipeRequest): Single<List<RecipeModel>> {
        return repository.getRecipes(request)
    }
}