package com.blogspot.android_czy_java.beautytips.viewmodel.account

import com.blogspot.android_czy_java.beautytips.livedata.common.NetworkNeededNotAvailableLiveData
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.CreateUserListRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.LoadRecipesFromUserListUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.common.NestedRecipeListViewModel

class UserListViewModel(
        createUserListRequestsUseCase: CreateUserListRequestsUseCase,
        loadListDataUseCase: LoadRecipesFromUserListUseCase,
        networkNeededNotAvailableLiveData: NetworkNeededNotAvailableLiveData,
        loginUseCase: LoginUseCase
) : NestedRecipeListViewModel<UserListRecipeRequest>
(
        createUserListRequestsUseCase,
        loadListDataUseCase,
        networkNeededNotAvailableLiveData,
        loginUseCase
)