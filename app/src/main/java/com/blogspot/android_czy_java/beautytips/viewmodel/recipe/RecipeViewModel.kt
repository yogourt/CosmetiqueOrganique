package com.blogspot.android_czy_java.beautytips.viewmodel.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class RecipeViewModel(private val loadRecipesUseCase: LoadRecipesUseCase) : ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred. "

    val recipeLiveData: MutableLiveData<RecipeUiModel> = MutableLiveData()

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

    private fun loadRecipes() {
        disposable.add(loadRecipesUseCase.execute(RecipeRequest(category, order))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    recipeLiveData.value = RecipeUiModel.RecipeLoading()
                }.subscribe(
                        { recipes ->
                            recipeLiveData.value = RecipeUiModel.RecipeSuccess(recipes)
                        },
                        { error ->
                            recipeLiveData.value = RecipeUiModel.RecipeLoadingError(error.message
                                    ?: defaultErrorMessage)
                        }
                ))
    }

}