package com.blogspot.android_czy_java.beautytips.viewmodel.detail

sealed class BaseDetailUiModel {

    class StatusLoading(): BaseDetailUiModel()

    class LoadingError(): BaseDetailUiModel()

    class LoadingSuccess(val data: BaseDetailData): BaseDetailUiModel()

}