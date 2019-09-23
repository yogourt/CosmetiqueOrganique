package com.blogspot.android_czy_java.beautytips.usecase.search

import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest

class SearchResultRequest(request: List<SearchResultInnerRequest>) : NestedListRequest<SearchResultInnerRequest>(request) {
    constructor(request: SearchResultInnerRequest): this(listOf(request))
}