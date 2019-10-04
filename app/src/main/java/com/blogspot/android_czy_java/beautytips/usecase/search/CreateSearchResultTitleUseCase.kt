package com.blogspot.android_czy_java.beautytips.usecase.search


class CreateSearchResultTitleUseCase()  {

    fun execute(recipeRequest: SearchResultRequest): String {
        return String.format("%s %s %s in %s: %s",
                recipeRequest.title,
                recipeRequest.author,
                recipeRequest.keywords,
                recipeRequest.category.getCategoryLabel(),
                recipeRequest.category.getSubcategoryLabel())
    }
}