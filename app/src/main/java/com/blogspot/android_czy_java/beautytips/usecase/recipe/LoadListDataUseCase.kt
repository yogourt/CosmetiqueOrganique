package com.blogspot.android_czy_java.beautytips.usecase.recipe

import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.InnerListData
import io.reactivex.Observable

open class LoadListDataUseCase(private val loadRecipesUseCase: LoadRecipesUseCase) {

    fun execute(request: MainListRequest): Observable<InnerListData> {

        return Observable.create {
            var counter = 0

            for (recipeRequest in request.requests) {
                loadRecipesUseCase.execute(recipeRequest)
                        .subscribe { result ->
                            it.onNext(InnerListData(result))
                            counter++
                            if(counter == request.requests.size) {
                                it.onComplete()
                            }
                        }
            }
        }
    }

}