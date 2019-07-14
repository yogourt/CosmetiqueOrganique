package com.blogspot.android_czy_java.beautytips.viewmodel

sealed class GenericUiModel<DATA_CLASS> {

    class StatusLoading<DATA_CLASS>: GenericUiModel<DATA_CLASS>()

    class LoadingError<DATA_CLASS>(message: String = ""): GenericUiModel<DATA_CLASS>()

    class LoadingSuccess<DATA_CLASS>(val data: DATA_CLASS): GenericUiModel<DATA_CLASS>() {
    }

}