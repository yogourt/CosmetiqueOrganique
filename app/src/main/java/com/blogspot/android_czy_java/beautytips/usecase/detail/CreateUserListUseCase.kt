package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.database.userlist.UserListModel
import com.blogspot.android_czy_java.beautytips.exception.detail.ListAlreadyExistsException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import io.reactivex.Single

class CreateUserListUseCase(private val userListRecipeRepository: UserListRecipeRepository,
                            private val getCurrentUserUseCase: GetCurrentUserUseCase) :
        UseCaseInterface<String, Boolean> {

    override fun execute(request: String): Single<Boolean> =
            Single.create {

                getCurrentUserUseCase.currentUserId()?.let { userId ->
                    if (userListRecipeRepository.getAllUserListTitles(userId)
                                    ?.map { it.toLowerCase() }
                                    ?.contains(request.trim().toLowerCase()) == true) {
                        it.onError(ListAlreadyExistsException())
                    }

                    userListRecipeRepository.createUserList(UserListModel(userId, request.trim(), " "))
                    it.onSuccess(true)
                }
            }
}