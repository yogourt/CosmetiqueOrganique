package com.blogspot.android_czy_java.beautytips.usecase.splash

import android.content.SharedPreferences
import com.blogspot.android_czy_java.beautytips.view.splash.SplashActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UpdateInBackgroundUseCase(private val fetchDataFromFirebaseUseCase: FetchDataFromFirebaseUseCase,
                                private val pushLocalDataUseCase: PushLocalDataUseCase,
                                private val prefs: SharedPreferences) {


    private val disposable = CompositeDisposable()

    fun execute() {
        disposable.add(pushLocalDataUseCase.execute()
                .subscribe { _ ->
                    fetchDataFromFirebaseUseCase.execute()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { _ ->
                                saveFetchTime()
                                disposable.dispose()
                            }
                }
        )
    }

    private fun saveFetchTime() {
        prefs.edit()
                .putLong(SplashActivity.KEY_LAST_FETCH_IN_MILLIS,
                        System.currentTimeMillis()).apply()
    }

}