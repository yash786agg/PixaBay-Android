package com.app.android_pixabay.datasource.api

sealed class NetworkState<T> {
    class Success<T> : NetworkState<T>()
    class Loading<T> : NetworkState<T>()
    class Error<T> : NetworkState<T>()
}