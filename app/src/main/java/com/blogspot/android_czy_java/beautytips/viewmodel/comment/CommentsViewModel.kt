package com.blogspot.android_czy_java.beautytips.viewmodel.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.usecase.comment.AddCommentUseCase
import com.blogspot.android_czy_java.beautytips.usecase.comment.LoadCommentsUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CommentsViewModel(private val loadCommentsUseCase: LoadCommentsUseCase,
                        private val addCommentUseCase: AddCommentUseCase) : ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred. "

    val commentListLiveData: MutableLiveData<GenericUiModel<List<CommentWithAuthorModel>>> = MutableLiveData()
    private val disposable = CompositeDisposable()

    private var recipeId: Long = 1

    fun init(recipeId: Long) {
        this.recipeId = recipeId
        loadComments(recipeId)
    }

    private fun loadComments(recipeId: Long) {
        disposable.add(loadCommentsUseCase.execute(recipeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { commentListLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe(
                        {
                            commentListLiveData.value = GenericUiModel.LoadingSuccess(it)
                        },
                        {
                            commentListLiveData.value = GenericUiModel.LoadingError(it.message
                                    ?: defaultErrorMessage)
                        }
                ))
    }

    fun addComment(newComment: String, responseTo: String?) {
        disposable.add(
                addCommentUseCase
                        .execute(newComment, responseTo, recipeId)
                        .subscribe()
        )
    }

}
