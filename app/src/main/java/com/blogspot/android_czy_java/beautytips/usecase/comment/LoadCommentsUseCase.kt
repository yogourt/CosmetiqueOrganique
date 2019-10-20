package com.blogspot.android_czy_java.beautytips.usecase.comment

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.blogspot.android_czy_java.beautytips.viewmodel.comment.CommentWithAuthorModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter

class LoadCommentsUseCase(private val repository: CommentRepository,
                          private val userRepository: UserRepository) {

    fun execute(request: Long): Observable<List<CommentWithAuthorModel>> =
            repository.getCommentsForRecipe(request).flatMap { comments ->
                Observable.create<List<CommentWithAuthorModel>> {

                    val list = mutableListOf<CommentWithAuthorModel>()

                    for (item in comments) {
                        list.add(
                                CommentWithAuthorModel(
                                        item,
                                        getAuthorSynchronously(item.authorId)
                                )
                        )
                    }
                    it.onNext(list)

                    val singleToZip = mutableListOf<Single<UserModel>>()

                    for (item in list) {
                        if (item.author == null) {
                            singleToZip.add(getAuthor(item.comment.authorId))
                        }
                    }

                    Single.zip(singleToZip) { results ->
                        for (result in results) {
                            if (result is UserModel) {
                                val commentWithAuthor =
                                        list.first { it.comment.authorId == result.id }
                                val index = list.indexOf(commentWithAuthor)
                                list[index] = CommentWithAuthorModel(commentWithAuthor.comment, result)
                            }
                        }
                        list
                    }.subscribe { newList ->
                        it.onNext(newList)
                    }
                }
            }

    private fun getAuthorSynchronously(firebaseId: String) =
            userRepository.getUserByFirebaseId(firebaseId)

    private fun getAuthor(firebaseId: String): Single<UserModel> {
        return Single.create { emitter ->
            userRepository.getUserByFirebaseId(firebaseId)?.let {
                emitter.onSuccess(it)
            } ?: getUserFromFirebase(firebaseId, emitter)
        }
    }

    private fun getUserFromFirebase(firebaseId: String,
                                    emitter: SingleEmitter<UserModel>) {
        userRepository.insertFirebaseUser(
                firebaseId,
                emitter,
                false
        )
    }

}