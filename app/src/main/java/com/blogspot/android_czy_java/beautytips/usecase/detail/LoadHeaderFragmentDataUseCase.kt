package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.blogspot.android_czy_java.beautytips.exception.common.DataNotFoundException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepositoryInterface
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderData
import io.reactivex.Single

class LoadHeaderFragmentDataUseCase(private val detailRepository: RecipeDetailRepositoryInterface,
                                    private val userListRecipeRepository: UserListRecipeRepository,
                                    private val currentUserUseCase: GetCurrentUserUseCase) :
        UseCaseInterface<Long, HeaderData> {

    override fun execute(request: Long): Single<HeaderData> {
        return Single.create {
            try {
                val title = detailRepository.getTitle(request)
                val imageUrl = detailRepository.getImageUrl(request)
                val category = detailRepository.getCategory(request)
                val subcategory = detailRepository.getSubcategory(request)
                val favNum = detailRepository.getFavNum(request)


                val inFav = currentUserUseCase.currentUserId()?.let { userId ->
                    userListRecipeRepository.getRecipeIdsInList(
                            userId, FirebaseKeys.KEY_USER_LIST_FAVORITES
                    ).contains(request)
                } ?: false

                it.onSuccess(HeaderData(title, imageUrl, category, subcategory, favNum, inFav))


            } catch (e: DataNotFoundException) {
                it.onError(DataNotFoundException())

            } catch (e: Exception) {
                it.onError(Exception("Unknown error occurred"))
            }
        }
    }

}