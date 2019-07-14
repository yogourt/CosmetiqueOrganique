package com.blogspot.android_czy_java.beautytips.viewmodel.detail

sealed class ImageFragmentUiModel {

    class StatusLoading(): ImageFragmentUiModel()

    class LoadingError(): ImageFragmentUiModel()

    data class LoadingSuccess(val data: ImageFragmentData): ImageFragmentUiModel()

}