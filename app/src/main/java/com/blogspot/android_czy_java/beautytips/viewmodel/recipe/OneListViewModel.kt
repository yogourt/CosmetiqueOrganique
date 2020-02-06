package com.blogspot.android_czy_java.beautytips.viewmodel.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.common.*
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

sealed class OneListViewModel<REQUEST: OneListRequest>(private val loadListDataUseCase: LoadRecipesUseCase<REQUEST>) :
        ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred. "

    val recipeListLiveData = MutableLiveData<GenericUiModel<OneListData>>()
    private val disposable = CompositeDisposable()

    var title = ""

    fun getList(request: REQUEST) {
        execute(request)
    }

    private fun execute(request: REQUEST) {
        disposable.add(loadListDataUseCase.execute(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { recipeListLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe(
                        {
                            recipeListLiveData.value = GenericUiModel.LoadingSuccess(it)
                        },
                        {
                            recipeListLiveData.value = GenericUiModel.LoadingError(it.message
                                    ?: defaultErrorMessage)
                        }
                ))
    }

    class OneRecipeListViewModel(loadListDataUseCase: LoadRecipesUseCase<RecipeRequest>) :
            OneListViewModel<RecipeRequest>(loadListDataUseCase)

    class OneUserRecipeListViewModel(loadListDataUseCase: LoadRecipesUseCase<UserListRecipeRequest>) :
            OneListViewModel<UserListRecipeRequest>(loadListDataUseCase)

    class SearchResultViewModel(loadListDataUseCase: LoadRecipesUseCase<SearchResultRequest>) :
            OneListViewModel<SearchResultRequest>(loadListDataUseCase)
}
