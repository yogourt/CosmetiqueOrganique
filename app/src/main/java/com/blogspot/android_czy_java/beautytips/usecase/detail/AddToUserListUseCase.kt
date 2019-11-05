package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase


class AddToUserListUseCase(private val userListRecipeRepository: UserListRecipeRepository,
                           private val currentUserUseCase: GetCurrentUserUseCase) {

    fun execute(request: AddToUserListRequest) {

        Thread(Runnable {
            currentUserUseCase.currentUserId()?.let { userId ->
                userListRecipeRepository.addToUserList(request.listName, request.recipeId, userId)
            }
        }).start()
    }
}