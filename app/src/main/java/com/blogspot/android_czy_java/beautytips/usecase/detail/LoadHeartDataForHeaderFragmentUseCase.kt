package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.blogspot.android_czy_java.beautytips.exception.account.UserNotLoggedInException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderHeartData
import io.reactivex.Single

class LoadHeartDataForHeaderFragmentUseCase(private val detailRepository: RecipeDetailRepository,
                                            private val userListRecipeRepository: UserListRecipeRepository,
                                            private val currentUserUseCase: GetCurrentUserUseCase) :
        UseCaseInterface<Long, HeaderHeartData> {

    override fun execute(request: Long): Single<HeaderHeartData> =

            Single.create {

                try {
                    val favNum = detailRepository.getFavNum(request)

                    val inFav = isInFav(request)

                    it.onSuccess(HeaderHeartData(favNum, inFav))

                } catch (e: Exception) {
                    it.onError(e)
                }

            }

    fun handleHeartClick(request: Long): Single<HeaderHeartData> {
        return Single.create { emitter ->
            if (currentUserUseCase.isUserAnonymousOrNull()) {
                emitter.onError(UserNotLoggedInException())
            }
            currentUserUseCase.currentUserId()?.let {
                if (isInFav(request)) {
                    userListRecipeRepository.removeFromUserList(FirebaseKeys.KEY_USER_LIST_FAVORITES, request, it)
                } else {
                    userListRecipeRepository.addToUserList(FirebaseKeys.KEY_USER_LIST_FAVORITES, request, it)
                }
                execute(request).subscribe(
                        { result -> emitter.onSuccess(result) },
                        { error -> emitter.onError(error) }
                )
            }
        }

    }


    private fun isInFav(request: Long): Boolean {
        return currentUserUseCase.currentUserId()?.let { userId ->
            userListRecipeRepository.getRecipeIdsInList(
                    userId, FirebaseKeys.KEY_USER_LIST_FAVORITES
            ).contains(request)
        } ?: false
    }
}