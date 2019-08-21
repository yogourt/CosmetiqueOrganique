package com.blogspot.android_czy_java.beautytips.viewmodel.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.recipe.CreateRecipeRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadListDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class RecipeViewModel(
        private val createRecipeRequestsUseCase: CreateRecipeRequestsUseCase,
        private val loadListDataUseCase: LoadListDataUseCase) : ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred. "

    val listData = MainListData(arrayListOf())
    val mainFragmentLiveData: MutableLiveData<GenericUiModel<MainListData>> = MutableLiveData()

    private val disposable = CompositeDisposable()

    var category: CategoryInterface = CategoryAll.SUBCATEGORY_ALL
        set(category) {
            if (category != this.category) {
                field = category
                loadRecipes()
            }
        }

    var order: Order = Order.NEW
        set(order) {
            if (order != this.order) {
                field = order
                loadRecipes()
            }
        }

    fun init() {
        loadRecipes()
    }

    fun retry() {
        loadRecipes()
    }

    private fun loadRecipes() {
        disposable.add(loadListDataUseCase.execute(createRecipeRequestsUseCase.execute())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mainFragmentLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        {
                            this.onNextRecipeList(it)
                        },
                        { error ->
                            mainFragmentLiveData.value = GenericUiModel.LoadingError(
                                    error.message ?: defaultErrorMessage)
                        }
                ))
    }

    private fun onNextRecipeList(recipes: InnerListData) {
        listData.data.add(recipes)
        mainFragmentLiveData.value = GenericUiModel.LoadingSuccess(listData)
    }

}