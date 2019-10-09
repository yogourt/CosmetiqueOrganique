package com.blogspot.android_czy_java.beautytips.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.comment.GetCommentNumberUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailBottomActionViewModel(private val getCommentNumberUseCase: GetCommentNumberUseCase) :
        ViewModel() {

    private val defaultErrorMessage = "An error occurred."

    val commentNumberLiveData = MutableLiveData<GenericUiModel<Int>>()
    private val disposable = CompositeDisposable()

    fun init(recipeId: Long) {
        getCommentNumber(recipeId)
    }

    private fun getCommentNumber(recipeId: Long) {
        disposable.add(getCommentNumberUseCase.execute(recipeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { commentNumberLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe(
                        {
                            commentNumberLiveData.value = GenericUiModel.LoadingSuccess(it)
                        },
                        {
                            commentNumberLiveData.value = GenericUiModel.LoadingError(it.message
                                    ?: defaultErrorMessage)
                        }
                ))
    }

}