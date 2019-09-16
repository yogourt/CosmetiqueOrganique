package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.exception.common.DataNotFoundException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DescriptionFragmentData
import io.reactivex.Single

class LoadDescriptionFragmentDataUseCase(private val detailRepositoryInterface: RecipeDetailRepositoryInterface):
        UseCaseInterface<Long, DescriptionFragmentData> {

    override fun execute(request: Long): Single<DescriptionFragmentData> {
        return Single.create {
            try {
                val description = detailRepositoryInterface.getDescription(request)
                val source = detailRepositoryInterface.getSource(request)

                it.onSuccess(DescriptionFragmentData(description, source))

            } catch (e: DataNotFoundException) {
                it.onError(DataNotFoundException())
            }
        }
    }
}