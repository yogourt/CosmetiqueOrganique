package com.blogspot.android_czy_java.beautytips.usecase.account.userlist

import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.NestedListRequestUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.UserListRecipeRequest
import io.reactivex.Single

class CreateUserListRequestsUseCase(private val currentUserUseCase: GetCurrentUserUseCase,
                                    private val userListRecipeRepository: UserListRecipeRepository) :
        NestedListRequestUseCase<UserListRecipeRequest> {
    override fun execute(): Single<NestedListRequest<UserListRecipeRequest>> {
        return Single.create {
            val requests = arrayListOf<UserListRecipeRequest>()

            currentUserUseCase.currentUserId()?.let { userId ->
                userListRecipeRepository.getAllUserListTitles(userId)?.forEach { title ->
                    requests.add(UserListRecipeRequest(title, userId, Order.NEW))
                }
            }
            it.onSuccess(UserListRequest(requests))
        }
    }

}