package com.blogspot.android_czy_java.beautytips.viewmodel.search

import com.blogspot.android_czy_java.beautytips.usecase.search.CreateSearchResultRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.search.LoadSearchResultListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultInnerRequest
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.common.NestedRecipeListViewModel

class SearchActivityViewModel(createSearchResultRequestsUseCase: CreateSearchResultRequestsUseCase,
                              loadSearchResultListDataUseCase: LoadSearchResultListDataUseCase
) : NestedRecipeListViewModel<SearchResultInnerRequest>(createSearchResultRequestsUseCase,
        loadSearchResultListDataUseCase) {

    fun search(request: SearchResultInnerRequest) {
        loadRecipes(SearchResultRequest(request))
    }
}