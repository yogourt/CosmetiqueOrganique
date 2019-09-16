package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.exception.common.DataNotFoundException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderData
import io.reactivex.Single

class LoadHeaderFragmentDataUseCase(private val detailRepository: RecipeDetailRepositoryInterface) :
        UseCaseInterface<Long, HeaderData> {

    override fun execute(request: Long): Single<HeaderData> {
        return Single.create {
            try {
                val title = detailRepository.getTitle(request)
                val imageUrl = detailRepository.getImageUrl(request)
                val category = detailRepository.getCategory(request)
                val subcategory = detailRepository.getSubcategory(request)

                it.onSuccess(HeaderData(title, imageUrl, category, subcategory))

            } catch (e: DataNotFoundException) {
                it.onError(DataNotFoundException())
            }
        }
    }

}