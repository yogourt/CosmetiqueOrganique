package com.blogspot.android_czy_java.beautytips.usecase.common

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.InnerListData
import io.reactivex.Single
import kotlin.math.min

abstract class LoadNestedListDataUseCase<RECIPE_REQUEST>(
        private val loadRecipesUseCase: LoadRecipesUseCase<RECIPE_REQUEST>,
        private val recipeRepositoryInterface: RecipeRepositoryInterface<RECIPE_REQUEST>) {

    fun isDatabaseEmpty(): Single<Boolean> = Single.create {
        it.onSuccess(recipeRepositoryInterface.getAllRecipesIds().isEmpty())
    }

    fun execute(request: NestedListRequest<RECIPE_REQUEST>): Single<List<InnerListData>> {

        return Single.create {
            val outerListData = arrayListOf<InnerListData>()

            for (recipeRequest in request.requests) {

                loadRecipesUseCase.execute(recipeRequest)
                        .subscribe(
                                { result ->
                                    outerListData.add(request.requests.indexOf(recipeRequest),
                                            prepareList(result,
                                                    recipeRequest,
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

    private fun prepareList(list: List<RecipeModel>,
                            recipeRequest: RECIPE_REQUEST, shouldPassAll: Boolean): InnerListData {

        return InnerListData(if (!shouldPassAll) {
            list.subList(0, min(15, list.size))
        } else {
            list
        }, createListTitle(recipeRequest))
    }

    abstract fun createListTitle(recipeRequest: RECIPE_REQUEST): String
}