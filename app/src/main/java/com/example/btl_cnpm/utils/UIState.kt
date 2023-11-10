package com.example.btl_cnpm.utils

sealed class UIState<T> {
    data class Success<T>(val data: T) : UIState<T>()
    data class Failure<T>(val message: String?) : UIState<T>()
}
