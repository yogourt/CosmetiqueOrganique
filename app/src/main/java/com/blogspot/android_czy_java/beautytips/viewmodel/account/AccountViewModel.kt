package com.blogspot.android_czy_java.beautytips.viewmodel.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.livedata.common.NetworkLiveData
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.firebase.ui.auth.AuthUI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AccountViewModel(private val loginUseCase: LoginUseCase,
                       private val getCurrentUserUseCase: GetCurrentUserUseCase,
                       private val networkLiveData: NetworkLiveData) : ViewModel() {


    private val defaultErrorMessage = "Sorry, an error occurred "

    val requestCode = 123

    private val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().setPermissions(listOf("public_profile")).build())

    private val disposable = CompositeDisposable()

    val userLiveData = MutableLiveData<GenericUiModel<UserModel>>()

    fun init() {
        if (!isUserAnonymous()) {
            loadUser()
        } else {
            userLiveData.value = null
        }
    }

    private fun loadUser() {
        disposable.add(getCurrentUserUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { userLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe(
                        {
                            userLiveData.value = GenericUiModel.LoadingSuccess(it)
                        },
                        {
                            userLiveData.value = GenericUiModel.LoadingError(it.message
                                    ?: defaultErrorMessage)
                        }
                ))
    }

    fun loginUser() {
        disposable.add(loginUseCase.saveAndReturnUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { userLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe(
                        {
                            userLiveData.value = GenericUiModel.LoadingSuccess(it)
                        },
                        {
                            userLiveData.value = GenericUiModel.LoadingError(it.message
                                    ?: defaultErrorMessage)
                        }
                ))
    }

    fun updateUserData(user: UserModel) {
        disposable.add(loginUseCase.updateUserData(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { userLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe(
                        {
                            userLiveData.value = GenericUiModel.LoadingSuccess(it)
                        },
                        {
                            userLiveData.value = GenericUiModel.LoadingError(it.message
                                    ?: defaultErrorMessage)
                        }
                ))

    }

    private fun isUserAnonymous(): Boolean {
        return loginUseCase.isUserAnonymousOrNull() ?: let {
            loginUseCase.loginAnonymously()
            true
        }
    }

    fun buildIntentForLoginActivity() = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(providers)
            .setTheme(R.style.LoginStyle)
            .setLogo(R.drawable.withoutback)
            .build()

    fun logout() {
        loginUseCase.loginAnonymously()
        userLiveData.value = null
    }

    fun isNetworkConnection(): Boolean = networkLiveData.isConnection()
}