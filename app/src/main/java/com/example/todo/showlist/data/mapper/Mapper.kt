package com.example.todo.showlist.data.mapper

import android.net.http.HttpException
import com.example.todo.showlist.domain.model.ApiError
import com.example.todo.showlist.domain.model.NetworkError
import okio.IOException

fun Throwable.toNetworkError(): NetworkError{
    val error = when(this){
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }
    return NetworkError(
        error = error,
        t = this
    )
}