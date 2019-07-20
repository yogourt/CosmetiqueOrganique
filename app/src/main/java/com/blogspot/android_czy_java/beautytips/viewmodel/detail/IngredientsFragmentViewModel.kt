package com.blogspot.android_czy_java.beautytips.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadIngredientsUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class IngredientsFragmentViewModel(private val loadIngredientsUseCase: LoadIngredientsUseCase): ViewModel() {

    val ingredientsFragmentLiveData = MutableLiveData<GenericUiModel<IngredientsFragmentData>>()

    private val disposable = CompositeDisposable()

    fun init(id: Long) {
        getDataForIngredientsFragment(id)
    }

    private fun getDataForIngredientsFragment(id: Long) {
        disposable.add(loadIngredientsUseCase.execute(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    ingredientsFragmentLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        { data ->
                            ingredientsFragmentLiveData.value = GenericUiModel.LoadingSuccess(data)
                        },
                        { _ ->
                            ingredientsFragmentLiveData.value = GenericUiModel.LoadingError()
                        }
                ))
    }

}