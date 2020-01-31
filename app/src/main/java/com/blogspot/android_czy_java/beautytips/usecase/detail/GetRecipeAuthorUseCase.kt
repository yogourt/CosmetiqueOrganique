package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetUserFromFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import io.reactivex.Single

class GetRecipeAuthorUseCase(private val detailRepository: RecipeDetailRepository,
                             private val getUserFromFirebaseUseCase: GetUserFromFirebaseUseCase)
    : UseCaseInterface<Long, UserModel> {

    override fun execute(request: Long): Single<UserModel> {
        return Single.create { emitter ->
            detailRepository.getAuthor(request)?.let {
                getUserFromFirebaseUseCase.execute(it).subscribe { user ->
                    emitter.onSuccess(user)
                }
            }
        }
    }
}