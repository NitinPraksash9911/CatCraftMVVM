package com.example.catcraft.arch

import com.nitin.networkerrorhandler.utils.ErrorResponse

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Error(val errorResponse: ErrorResponse) : ViewState<Nothing>()
    data class Success<T>(val item: T) : ViewState<T>()
}

