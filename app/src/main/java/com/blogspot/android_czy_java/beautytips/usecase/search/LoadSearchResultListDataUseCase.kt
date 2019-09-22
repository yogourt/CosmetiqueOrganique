package com.blogspot.android_czy_java.beautytips.usecase.search

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadNestedListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadRecipesUseCase

class LoadSearchResultListDataUseCase(loadRecipesUseCase: LoadRecipesUseCase<SearchResultRequest>,
                                      recipeRepositoryInterface: RecipeRepositoryInterface<SearchResultRequest>
) :
        LoadNestedListDataUseCase<SearchResultRequest>(loadRecipesUseCase,
                recipeRepositoryInterface) {

    override fun createListTitle(recipeRequest: SearchResultRequest): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}