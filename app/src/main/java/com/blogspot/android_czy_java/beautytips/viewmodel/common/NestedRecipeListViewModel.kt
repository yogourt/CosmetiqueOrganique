package com.blogspot.android_czy_java.beautytips.viewmodel.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadNestedListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.OneListRequest
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class NestedRecipeListViewModel<RECIPE_REQUEST : OneListRequest>(
        private val nestedListRequestUseCase: NestedListRequestUseCase<RECIPE_REQUEST>,
        private val loadListDataUseCase: LoadNestedListDataUseCase<RECIPE_REQUEST>) : ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred. "

    val recipeListLiveData: MutableLiveData<GenericUiModel<MainListData>> = MutableLiveData()
    private val disposable = CompositeDisposable()

    private var requests: NestedListRequest<RECIPE_REQUEST>? = null

    open fun init() {
        loadRecipes(nestedListRequestUseCase.execute())
    }

    fun retry() {
        loadRecipes(nestedListRequestUseCase.execute())
    }

    fun getRequestForId(listId: Int): RECIPE_REQUEST? {
        return requests?.requests?.getOrNull(listId)
    }

    internal fun loadRecipes(requestSingle: Single<NestedListRequest<RECIPE_REQUEST>>) {

        disposable.add(requestSingle.flatMap { requests ->
            this.requests = requests
            loadListDataUseCase.execute(requests)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    recipeListLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        {
                            recipeListLiveData.value = GenericUiModel.LoadingSuccess(MainListData(it))
                        },
                        { error ->
                            recipeListLiveData.value = GenericUiModel.LoadingError(
                                    error.message ?: defaultErrorMessage)
                        }
                ))
    }

}