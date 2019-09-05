package com.blogspot.android_czy_java.beautytips.viewmodel.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.livedata.common.NetworkNeededNotAvailableLiveData
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadNestedListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.InnerListData
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class NestedRecipeListViewModel<RECIPE_REQUEST>(
        private val createRecipeRequestsUseCase: NestedListRequestUseCase<RECIPE_REQUEST>,
        private val loadListDataUseCase: LoadNestedListDataUseCase<RECIPE_REQUEST>) : ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred. "

    private val listData = MainListData(arrayListOf())
    val recipeListLiveData: MutableLiveData<GenericUiModel<MainListData>> = MutableLiveData()
    private val disposable = CompositeDisposable()

    open fun init() {
        checkIfNetworkNeeded()
        loadRecipes()
    }

    fun retry() {
        loginUseCase.loginAnonymouslyIfNull()
        listData.data.clear()
        loadRecipes()
    }

    private fun loadRecipes() {
        if(loginUseCase.isUserNull() || networkNeededNotAvailableLiveData.value == true) {
           return
        }

        disposable.add(loadListDataUseCase.execute(createRecipeRequestsUseCase.execute())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    recipeListLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        {
                            this.onNextRecipeList(it)
                        },
                        { error ->
                            recipeListLiveData.value = GenericUiModel.LoadingError(
                                    error.message ?: defaultErrorMessage)
                        }, {
                    networkNeededNotAvailableLiveData.onNetworkNotNeeded()
                }
                ))
    }

    private fun onNextRecipeList(recipes: InnerListData) {
        listData.data.add(recipes)
        recipeListLiveData.value = GenericUiModel.LoadingSuccess(listData)
    }

    private fun checkIfNetworkNeeded() {
        disposable.add(loadListDataUseCase.isDatabaseEmpty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { databaseEmpty ->
                    if (databaseEmpty) {
                        networkNeededNotAvailableLiveData.onNetworkNeeded()
                    }
                }
        )
    }

    fun onNetworkAvailableOrNotNeeded() {
        if(recipesShouldBeReloaded()) {
            retry()
        }
    }

    private fun recipesShouldBeReloaded() = recipeListLiveData.value == null
            || recipeListLiveData.value is GenericUiModel.LoadingError

}