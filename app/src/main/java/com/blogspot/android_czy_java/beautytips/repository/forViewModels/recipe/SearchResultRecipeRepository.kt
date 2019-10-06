package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListData
import io.reactivex.Single

class SearchResultRecipeRepository(private val recipeRepository:
                                   RecipeRepositoryInterface<RecipeRequest>) :
        RecipeRepositoryInterface<SearchResultRequest>(recipeRepository.recipeDao) {

    override fun getRecipes(request: SearchResultRequest): Single<OneListData> {

        return recipeRepository.getRecipes(
                RecipeRequest(request.category,
                        request.order))
                .map {
                    if (titleAndKeywordsBlank(request.title, request.keywords)) {
                        return@map it
                    } else {
                        OneListData(it.data.filter { recipe ->
                            (prepareExpression(recipe)).let { exp ->
                                checkIfTitleMatches(exp, request.title) ||
                                        checkIfKeywordsMatch(exp, request.keywords)
                            }
                        },
                                createTitle(request))
                    }
                }

    }

    private fun prepareExpression(recipe: RecipeModel): String {
        return recipe.title.toLowerCase() + " " + recipe.tags.toLowerCase()
    }

    private fun titleAndKeywordsBlank(title: String, keywords: String): Boolean {
        return title.isBlank() && keywords.isBlank()
    }

    private fun checkIfTitleMatches(expression: String, title: String): Boolean {
        return title.isNotBlank() && expression.contains(title.trim().toLowerCase())
    }

    private fun checkIfKeywordsMatch(expression: String, keywords: String): Boolean {
        return keywords.isNotBlank() && keywords.split(" ", ",")
                .map { keyword -> keyword.trim().toLowerCase() }
                .any {
                    expression.contains(it)
                }
    }

    private fun createTitle(request: SearchResultRequest) =
            "Searching for: ${request.title} ${request.keywords}"
}



