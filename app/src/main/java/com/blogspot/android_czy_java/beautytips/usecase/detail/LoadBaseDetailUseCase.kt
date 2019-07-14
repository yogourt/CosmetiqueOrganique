package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.repository.exception.DataNotFoundException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.BaseDetailData
import io.reactivex.Single

class LoadBaseDetailUseCase(private val detailRepositoryInterface: RecipeDetailRepositoryInterface):
        UseCaseInterface<Long, BaseDetailData> {

    override fun execute(request: Long): Single<BaseDetailData> {
        return Single.create {
            try {
                val description = detailRepositoryInterface.getDescription(request)
                val ingredients = detailRepositoryInterface.getIngredients(request)
                val source = detailRepositoryInterface.getSource(request)

                it.onSuccess(BaseDetailData(description, ingredients, source))

            } catch (e: DataNotFoundException) {
                it.onError(DataNotFoundException())
            }
        }
    }
}