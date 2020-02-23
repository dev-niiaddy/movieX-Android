package com.orbilax.moviex.util

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.HttpException


fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun <T: Any>  handleSuccess(data: T, onSuccess: (Result.Success<T>)-> Unit) {
    onSuccess(Result.Success(data))
}

fun handleApiError(throwable: Throwable, onFailure: (Result.Error) -> Unit) {
    if (throwable is HttpException) {
        onFailure(
            Result.Error(
                ApiError(throwable.message(), throwable.code())
            )
        )
    }
}