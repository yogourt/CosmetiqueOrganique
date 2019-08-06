package com.blogspot.android_czy_java.beautytips.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadDescriptionFragmentDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DescriptionFragmentViewModel(
        private val loadDescriptionFragmentDataUseCase: LoadDescriptionFragmentDataUseCase) : ViewModel() {

    val descriptionLiveData = MutableLiveData<GenericUiModel<DescriptionFragmentData>>()

    private val disposable = CompositeDisposable()

    fun init(recipeId: Long) {
        getDataForDetaiDescriptionFragment(recipeId)
    }

    private fun getDataForDetaiDescriptionFragment(id: Long) {
        disposable.add(loadDescriptionFragmentDataUseCase.execute(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    descriptionLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        { data ->
                            descriptionLiveData.value = GenericUiModel.LoadingSuccess(data)
                        },
                        {
                            descriptionLiveData.value = GenericUiModel.LoadingError()
                        }
                ))
    }

}