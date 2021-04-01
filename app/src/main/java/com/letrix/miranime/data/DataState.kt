package com.letrix.miranime.data

sealed class DataState<out R> {

    object Empty : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()

}