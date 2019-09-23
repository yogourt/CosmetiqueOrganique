package com.blogspot.android_czy_java.beautytips.usecase.search

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadNestedListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadRecipesUseCase

class LoadSearchResultListDataUseCase(loadRecipesUseCase: LoadRecipesUseCase<SearchResultInnerRequest>,
                                      recipeRepositoryInterface: RecipeRepositoryInterface<SearchResultInnerRequest>
) :
        LoadNestedListDataUseCase<SearchResultInnerRequest>(loadRecipesUseCase,
                recipeRepositoryInterface) {

    override fun createListTitle(recipeRequest: SearchResultInnerRequest): String {
        return String.format("%s %s %s in %s: %s",
                recipeRequest.title,
                recipeRequest.author,
                recipeRequest.keywords,
                recipeRequest.category.getCategoryLabel(),
                recipeRequest.category.getSubcategoryLabel())
    }
}