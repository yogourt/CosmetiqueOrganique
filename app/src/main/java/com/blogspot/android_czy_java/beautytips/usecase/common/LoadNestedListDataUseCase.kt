package com.blogspot.android_czy_java.beautytips.usecase.common

import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.InnerListData
import io.reactivex.Observable
import kotlin.math.min

abstract class LoadNestedListDataUseCase<RECIPE_REQUEST>(
        private val loadRecipesUseCase: LoadRecipesUseCase<RECIPE_REQUEST>) {

    fun execute(request: NestedListRequest<RECIPE_REQUEST>): Observable<InnerListData> {

        return Observable.create {
            var counter = 0

            for (recipeRequest in request.requests) {
                loadRecipesUseCase.execute(recipeRequest)
                        .subscribe(
                                { result ->
                                    it.onNext(InnerListData(result.subList(0, min(15, result.size)),
                                            createListTitle(recipeRequest)))
                                    counter++
                                    if (counter == request.requests.size) {
                                        it.onComplete()
                                    }
                                },
                                { error ->
                                    it.onError(error)
                                }
                        )
            }
        }
    }

    abstract fun createListTitle(recipeRequest: RECIPE_REQUEST): String
}