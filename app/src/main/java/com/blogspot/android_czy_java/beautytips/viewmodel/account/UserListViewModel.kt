package com.blogspot.android_czy_java.beautytips.viewmodel.account

import com.blogspot.android_czy_java.beautytips.usecase.account.CreateUserListRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.LoadRecipesFromUserListUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.common.NestedRecipeListViewModel

class UserListViewModel(
        createUserListRequestsUseCase: CreateUserListRequestsUseCase,
        loadListDataUseCase: LoadRecipesFromUserListUseCase
) : NestedRecipeListViewModel<UserListRecipeRequest>
(
        createUserListRequestsUseCase,
        loadListDataUseCase
)