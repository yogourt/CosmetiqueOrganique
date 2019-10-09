package com.blogspot.android_czy_java.beautytips.usecase.comment

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import io.reactivex.Single

class GetCommentNumberUseCase(private val commentRepository: CommentRepository) :
        UseCaseInterface<Long, Int> {

    override fun execute(request: Long): Single<Int> {
        return Single.create {
            val count = commentRepository.getCommentNumberForRecipe(request)
            it.onSuccess(count)
        }
    }
}