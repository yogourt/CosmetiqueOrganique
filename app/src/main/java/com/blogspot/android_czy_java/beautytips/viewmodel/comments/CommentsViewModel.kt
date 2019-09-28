package com.blogspot.android_czy_java.beautytips.viewmodel.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.usecase.comment.LoadCommentsUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CommentsViewModel(private val loadCommentsUseCase: LoadCommentsUseCase) : ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred. "

    val commentListLiveData: MutableLiveData<GenericUiModel<List<CommentModel>>> = MutableLiveData()
    private val disposable = CompositeDisposable()

    fun init(recipeId: Long) {
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

}
