package com.blogspot.android_czy_java.beautytips.usecase.comment

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetUserFromFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.comment.CommentWithAuthorModel
import io.reactivex.Observable
import io.reactivex.Single

class LoadCommentsUseCase(private val repository: CommentRepository,
                          private val userRepository: UserRepository,
                          private val getUserFromFirebaseUseCase: GetUserFromFirebaseUseCase) {

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
                    it.onNext(sort(list))

                    val singleToZip = mutableListOf<Single<UserModel>>()

                    for (item in list) {
                        if (item.author == null) {
                            singleToZip.add(getUserFromFirebaseUseCase.execute(item.comment.authorId))
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

                        it.onNext(sort(newList))
                    }
                }
            }

    private fun getAuthorSynchronously(firebaseId: String) =
            userRepository.getUserByFirebaseId(firebaseId)

    private fun sort(list: List<CommentWithAuthorModel>): List<CommentWithAuthorModel> {
        val tempList = mutableListOf<CommentWithAuthorModel>()

        for (commentWithAuthor in list) {
            if (commentWithAuthor.comment.responseTo == null) {
                tempList.add(commentWithAuthor)
            }
        }

        for (commentWithAuthor in list) {
            commentWithAuthor.comment.responseTo?.let { responseTo ->
                val commentIndex = tempList.indexOfFirst { it.comment.firebaseId == responseTo }
                val comment = tempList[commentIndex]
                comment.subcomments.add(commentWithAuthor)
                tempList[commentIndex] = comment
            }
        }

        tempList.sortBy { it.comment.firebaseId }

        for (commentWithAuthor in tempList) {
            if (commentWithAuthor.subcomments.isNotEmpty()) {
                commentWithAuthor.subcomments.sortBy { it.comment.firebaseId }
            }
        }

        val newList = mutableListOf<CommentWithAuthorModel>()

        for (commentWithAuthor in tempList) {
            newList.add(commentWithAuthor)
            newList.addAll(commentWithAuthor.subcomments)
            commentWithAuthor.subcomments.clear()
        }

        return newList
    }

}