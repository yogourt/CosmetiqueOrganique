package com.blogspot.android_czy_java.beautytips.usecase.comment

import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddCommentUseCase(private val getCurrentUserUseCase: GetCurrentUserUseCase,
                        private val commentRepository: CommentRepository,
                        private val pushCommentToFirebaseUseCase: PushCommentToFirebaseUseCase) {

    private val disposable = CompositeDisposable()

    fun execute(message: String, responseTo: String?, recipeId: Long): Single<Boolean> {

        return Single.create { emitter ->
            disposable.add(getCurrentUserUseCase.execute()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { user ->
                                val comment = CommentModel(
                                        null,
                                        responseTo,
                                        recipeId,
                                        user.id,
                                        message
                                )

                                saveComment(comment)
                                disposable.dispose()

                            },
                            {
                                emitter.onError(it)
                            })
            )
        }
    }

    private fun saveComment(comment: CommentModel) {
        Thread(Runnable {
            comment.id = commentRepository.addComment(comment)
            pushCommentToFirebaseUseCase.execute(comment)
        }).start()
    }
}