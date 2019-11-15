package com.blogspot.android_czy_java.beautytips.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.*
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class HeaderViewModel(private val loadHeaderFragmentDataUseCase: LoadHeaderFragmentDataUseCase,
                      private val loadHeartDataForHeaderFragmentUseCase: LoadHeartDataForHeaderFragmentUseCase,
                      private val addToUserListUseCase: AddToUserListUseCase,
                      private val createUserListUseCase: CreateUserListUseCase)
    : ViewModel() {

    private val defaultErrorMessage = "Sorry, an error occurred"

    val headerFragmentLiveData = MutableLiveData<GenericUiModel<HeaderData>>()
    val headerHeartLiveData = MutableLiveData<GenericUiModel<HeaderHeartData>>()

    val errorLiveData = MutableLiveData<Throwable>()

    private val disposable = CompositeDisposable()

    private var recipeId = 1L

    fun init(id: Long) {
        recipeId = id
        getDataForHeaderFragment(id)
        getDataForHeart()
    }

    private fun getDataForHeaderFragment(id: Long) {
        disposable.add(loadHeaderFragmentDataUseCase.execute(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    headerFragmentLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        { data ->
                            headerFragmentLiveData.value = GenericUiModel.LoadingSuccess(data)
                        },
                        {
                            headerFragmentLiveData.value = GenericUiModel.LoadingError()
                        }
                ))
    }

    private fun getDataForHeart() {
        disposable.add(loadHeartDataForHeaderFragmentUseCase.execute(recipeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    headerHeartLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        { data ->
                            headerHeartLiveData.value = GenericUiModel.LoadingSuccess(data)
                        },
                        {
                            headerHeartLiveData.value = GenericUiModel.LoadingError()
                        }
                ))
    }

    fun handleHeartClick() {
        disposable.add(loadHeartDataForHeaderFragmentUseCase.handleHeartClick(recipeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    headerHeartLiveData.value = GenericUiModel.StatusLoading()
                }.subscribe(
                        { data ->
                            headerHeartLiveData.value = GenericUiModel.LoadingSuccess(data)
                        },
                        {
                            headerHeartLiveData.value = GenericUiModel.LoadingError(it.message
                                    ?: defaultErrorMessage)
                        }
                ))
    }

    fun saveToList(listName: String) {
        disposable.add(addToUserListUseCase.execute(AddToUserListRequest(recipeId, listName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    if (listName == "Favorites") {
                        getDataForHeart()
                    }
                }.subscribe())

    }

    fun addList(listName: String, doAfter: () -> Unit) {
        disposable.add(createUserListUseCase.execute(listName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { doAfter() },
                        { error -> errorLiveData.value = error }
                )
        )


    }
}