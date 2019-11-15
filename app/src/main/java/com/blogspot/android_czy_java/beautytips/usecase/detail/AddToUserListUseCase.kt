package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import io.reactivex.Single


class AddToUserListUseCase(private val userListRecipeRepository: UserListRecipeRepository,
                           private val currentUserUseCase: GetCurrentUserUseCase) :
        UseCaseInterface<AddToUserListRequest, Boolean> {

    override fun execute(request: AddToUserListRequest): Single<Boolean> =

            Single.create {
                currentUserUseCase.currentUserId()?.let { userId ->
                    userListRecipeRepository.addToUserList(request.listName, request.recipeId, userId)
                }
                it.onSuccess(true)
            }

}