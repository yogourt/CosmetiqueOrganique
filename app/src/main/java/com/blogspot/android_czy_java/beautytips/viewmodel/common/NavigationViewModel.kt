package com.blogspot.android_czy_java.beautytips.viewmodel.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.notification.GetNotificationNumberUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NavigationViewModel(private val getNotificationNumberUseCase: GetNotificationNumberUseCase)
    : ViewModel() {

    val notificationNumberLiveData: MutableLiveData<GenericUiModel<Int>> = MutableLiveData()

    val disposable = CompositeDisposable()

    fun init() {
        getNotificationNumber()
    }

    private fun getNotificationNumber() {
        disposable.add(getNotificationNumberUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    notificationNumberLiveData.value = GenericUiModel.LoadingSuccess(it)
                })

    }


}