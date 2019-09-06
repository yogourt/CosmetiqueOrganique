package com.blogspot.android_czy_java.beautytips.viewmodel.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.livedata.common.NetworkLiveData
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.usecase.splash.FetchDataFromFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SplashViewModel(private val fetchDataFromFirebaseUseCase: FetchDataFromFirebaseUseCase,
                      private val loginUseCase: LoginUseCase,
                      val networkLiveData: NetworkLiveData) : ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred. "

    val fetchSuccessLiveData = MutableLiveData<GenericUiModel<Boolean>>()
    private val disposable = CompositeDisposable()

    fun init() {
        loginUseCase.loginAnonymouslyIfNull()
        if (!loginUseCase.isUserNull()) {
            fetchAllData()
        }
    }

    fun retry() {
        init()
    }

    private fun fetchAllData() {
        disposable.add(fetchDataFromFirebaseUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { fetchSuccessLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe({ success ->
                    fetchSuccessLiveData.value = GenericUiModel.LoadingSuccess(success)
                }, { error ->
                    fetchSuccessLiveData.value = GenericUiModel.LoadingError(error.message
                            ?: defaultErrorMessage)
                })
        )
    }

    fun onNetworkAccessed() {
        if(fetchSuccessLiveData.value == null) {
            retry()
        }
    }


}