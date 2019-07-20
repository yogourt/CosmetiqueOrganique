package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.repository.exception.DataNotFoundException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderFragmentData
import io.reactivex.Single

class LoadHeaderFragmentDataUseCase(private val detailRepository: RecipeDetailRepositoryInterface) :
        UseCaseInterface<Long, HeaderFragmentData> {

    override fun execute(request: Long): Single<HeaderFragmentData> {
        return Single.create {
            try {
                val title = detailRepository.getTitle(request)
                val imageUrl = detailRepository.getImageUrl(request)
                val category = detailRepository.getCategory(request)
                val subcategory = detailRepository.getSubcategory(request)

                it.onSuccess(HeaderFragmentData(title, imageUrl, category, subcategory))

            } catch (e: DataNotFoundException) {
                it.onError(DataNotFoundException())
            }
        }
    }

}