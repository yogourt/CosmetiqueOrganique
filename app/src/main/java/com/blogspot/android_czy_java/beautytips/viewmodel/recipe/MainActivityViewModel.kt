package com.blogspot.android_czy_java.beautytips.viewmodel.recipe

import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase

class MainActivityViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                            private val loginUseCase: LoginUseCase) : ViewModel() {

    var detailScreenOpenTimesAfterInterstitialAd = 0

    init {
        loginUseCase.loginAnonymouslyIfNull()
    }

    fun shouldInterstitialAdBeShown(): Boolean {
        return detailScreenOpenTimesAfterInterstitialAd > 3
    }

    fun logInAnonymously() {
        loginUseCase.loginAnonymously()
    }

}
