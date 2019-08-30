package com.blogspot.android_czy_java.beautytips.viewmodel.account

import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.firebase.ui.auth.AuthUI
import java.util.*

class AccountViewModel(private val loginUseCase: LoginUseCase,
                       private val getCurrentUserUseCase: GetCurrentUserUseCase) : ViewModel() {

    val request_code_login = 123

    val login_providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().setPermissions(listOf("public_profile")).build())


    fun isUserLoggedIn() = (getCurrentUserUseCase.execute() != null)

}