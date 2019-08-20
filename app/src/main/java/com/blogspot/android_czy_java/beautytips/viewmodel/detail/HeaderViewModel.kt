package com.blogspot.android_czy_java.beautytips.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadHeaderFragmentDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HeaderViewModel(private val loadHeaderFragmentDataUseCase: LoadHeaderFragmentDataUseCase)
    : ViewModel() {

    val headerFragmentLiveData = MutableLiveData<GenericUiModel<HeaderData>>()

    private val disposable = CompositeDisposable()

    fun init(id: Long) {
        getDataForImageFragment(id)
    }

    private fun getDataForImageFragment(id: Long) {
        disposable.add(loadHeaderFragmentDataUseCase.execute(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    headerFragmentLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        { data ->
                            headerFragmentLiveData.value = GenericUiModel.LoadingSuccess(data)
                        },
                        {
                            headerFragmentLiveData.value = GenericUiModel.LoadingError()
                        }
                ))
    }
}