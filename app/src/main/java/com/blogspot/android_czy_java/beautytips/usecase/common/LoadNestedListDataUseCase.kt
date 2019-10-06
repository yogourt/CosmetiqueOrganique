package com.blogspot.android_czy_java.beautytips.usecase.common

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListData
import io.reactivex.Single
import kotlin.math.min

abstract class LoadNestedListDataUseCase<RECIPE_REQUEST: OneListRequest>(
        private val loadRecipesUseCase: LoadRecipesUseCase<RECIPE_REQUEST>,
        private val recipeRepositoryInterface: RecipeRepositoryInterface<RECIPE_REQUEST>) {

    fun isDatabaseEmpty(): Single<Boolean> = Single.create {
        it.onSuccess(recipeRepositoryInterface.getAllRecipesIds().isEmpty())
    }

    fun execute(request: NestedListRequest<RECIPE_REQUEST>): Single<List<OneListData>> {

        return Single.create {
            val outerListData = arrayListOf<OneListData>()

            for (recipeRequest in request.requests) {

                loadRecipesUseCase.execute(recipeRequest)
                        .subscribe(
                                { result ->
                                    outerListData.add(request.requests.indexOf(recipeRequest),
                                            prepareList(result,
                                                    request.requests.size == 1)
                                    )
                                    if (outerListData.size == request.requests.size) {
                                        it.onSuccess(outerListData)
                                    }
                                },
                                { error ->
                                    it.onError(error)
                                }
                        )
            }
        }
    }

    private fun prepareList(list: OneListData, shouldPassAll: Boolean): OneListData {

        return OneListData(if (!shouldPassAll) {
            list.data.subList(0, min(15, list.data.size))
        } else {
            list.data
        }, list.listTitle)
    }

}