package com.blogspot.android_czy_java.beautytips.usecase.comment

import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import io.reactivex.Single

class LoadCommentsUseCase(private val repository: CommentRepository):
        UseCaseInterface<Long, List<CommentModel>> {

    override fun execute(request: Long): Single<List<CommentModel>> = Single.create {
        it.onSuccess(repository.getCommentsForRecipe(request))
    }
}