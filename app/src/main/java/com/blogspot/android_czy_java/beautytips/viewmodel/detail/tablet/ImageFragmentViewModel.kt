package com.blogspot.android_czy_java.beautytips.viewmodel.detail.tablet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadImageFragmentDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.ImageFragmentUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImageFragmentViewModel(private val loadImageFragmentDataUseCase: LoadImageFragmentDataUseCase)
    : ViewModel() {

    val imageFragmentLiveData = MutableLiveData<ImageFragmentUiModel>()

    private val disposable = CompositeDisposable()

    fun init(id: Long) {
        getDataForImageFragment(id)
    }

    fun getDataForImageFragment(id: Long) {
        disposable.add(loadImageFragmentDataUseCase.execute(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    imageFragmentLiveData.value = ImageFragmentUiModel.StatusLoading()
                }.subscribe(
                        { data ->
                            imageFragmentLiveData.value = ImageFragmentUiModel.LoadingSuccess(data)
                        },
                        { _ ->
                            imageFragmentLiveData.value = ImageFragmentUiModel.LoadingError()
                        }
                ))
    }
}