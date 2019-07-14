package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.repository.exception.DataNotFoundException
import com.blogspot.android_czy_java.beautytips.repository.DetailRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.viewmodel.common.ImageFragmentData
import com.blogspot.android_czy_java.beautytips.viewmodel.common.ImageFragmentUiModel
import io.reactivex.Single

class LoadImageFragmentDataUseCase(private val detailRepositoryInterface: DetailRepositoryInterface) :
        UseCaseInterface<Long, ImageFragmentData> {

    override fun execute(request: Long): Single<ImageFragmentData> {
        return Single.create {
            try {
                val title = detailRepositoryInterface.getTitle(request)
                val imageUrl = detailRepositoryInterface.getImageUrl(request)

                it.onSuccess(ImageFragmentData(title, imageUrl))

            } catch (e: DataNotFoundException) {
                it.onError(DataNotFoundException())
            }
        }
    }

}