package com.blogspot.android_czy_java.beautytips.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadBaseDetailUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel(
        private val loadBaseDetailUseCase: LoadBaseDetailUseCase //,
        //TODO: private val loadCommentsUseCase: LoadCommentsUseCase,
        //TODO: private val loadFavNumUseCase: LoadFavNumUseCase
) : ViewModel() {

    val baseDetailLiveData = MutableLiveData<BaseDetailUiModel>()

    val disposable = CompositeDisposable()

    fun init(recipeId: Long) {
        getBaseDataForDetailFragment(recipeId)
    }

    fun getBaseDataForDetailFragment(id: Long) {
        disposable.add(loadBaseDetailUseCase.execute(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    baseDetailLiveData.value = BaseDetailUiModel.StatusLoading()
                }.subscribe(
                        { data ->
                            baseDetailLiveData.value = BaseDetailUiModel.LoadingSuccess(data)
                        },
                        {
                            baseDetailLiveData.value = BaseDetailUiModel.LoadingError()
                        }
                ))
    }

}