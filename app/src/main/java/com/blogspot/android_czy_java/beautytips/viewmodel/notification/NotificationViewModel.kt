package com.blogspot.android_czy_java.beautytips.viewmodel.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel
import com.blogspot.android_czy_java.beautytips.usecase.notification.GetNotificationsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.MakeNotificationSeenUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NotificationViewModel(private val getNotificationsUseCase: GetNotificationsUseCase,
                            private val makeNotificationSeenUseCase: MakeNotificationSeenUseCase) : ViewModel() {

    private val defaultErrorMessage = "An error occurred"

    val notificationLiveData = MutableLiveData<GenericUiModel<List<NotificationModel>>>()

    private val disposable = CompositeDisposable()

    fun init() {
        loadNotifications()
    }

    private fun loadNotifications() {
        disposable.add(getNotificationsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { notificationLiveData.value = GenericUiModel.StatusLoading() }
                .subscribe(
                        {
                            notificationLiveData.value = GenericUiModel.LoadingSuccess(it)
                        },
                        {
                            notificationLiveData.value = GenericUiModel.LoadingError(it.message
                                    ?: defaultErrorMessage)
                        }
                ))
    }

    fun makeNotificationSeen(notificationId: Int) {
        makeNotificationSeenUseCase.execute(notificationId)
    }
}


