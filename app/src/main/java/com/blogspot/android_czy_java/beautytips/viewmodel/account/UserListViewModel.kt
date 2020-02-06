package com.blogspot.android_czy_java.beautytips.viewmodel.account

import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.CreateUserListRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.LoadRecipesFromUserListUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.common.NestedRecipeListViewModel

class UserListViewModel(
        createUserListRequestsUseCase: CreateUserListRequestsUseCase,
        loadListDataUseCase: LoadRecipesFromUserListUseCase
) : NestedRecipeListViewModel<UserListRecipeRequest>
(
        createUserListRequestsUseCase,
        loadListDataUseCase
)