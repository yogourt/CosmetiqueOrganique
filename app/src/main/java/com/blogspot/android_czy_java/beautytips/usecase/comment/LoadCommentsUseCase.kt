package com.blogspot.android_czy_java.beautytips.usecase.comment

import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import io.reactivex.Observable

class LoadCommentsUseCase(private val repository: CommentRepository) {

    fun execute(request: Long): Observable<List<CommentModel>> = repository.getCommentsForRecipe(request)

}