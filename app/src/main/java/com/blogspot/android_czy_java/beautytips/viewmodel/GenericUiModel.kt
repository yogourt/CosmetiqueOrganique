package com.blogspot.android_czy_java.beautytips.viewmodel

import timber.log.Timber

sealed class GenericUiModel<DATA_CLASS> {

    class StatusLoading<DATA_CLASS>: GenericUiModel<DATA_CLASS>()

    class LoadingError<DATA_CLASS>(val message: String = "Unknown error occurred"): GenericUiModel<DATA_CLASS>() {
        init {
            Timber.e(message)
        }
    }

    class LoadingSuccess<DATA_CLASS>(val data: DATA_CLASS): GenericUiModel<DATA_CLASS>()

}