package com.blogspot.android_czy_java.beautytips.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.GetRecipeAuthorUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthorViewModel(private val getRecipeAuthorUseCase: GetRecipeAuthorUseCase) : ViewModel() {

    val authorLiveData = MutableLiveData<GenericUiModel<UserModel>>()

    private val disposable = CompositeDisposable()

    fun init(recipeId: Long) {
        getDataForDetailAuthorFragment(recipeId)
    }

    private fun getDataForDetailAuthorFragment(recipeId: Long) {
        disposable.add(getRecipeAuthorUseCase.execute(recipeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { authorLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe { user ->
                    authorLiveData.value = GenericUiModel.LoadingSuccess(user)
                })
    }

}


