package com.reyy.week10vp.soal2.util

sealed class MusicResource<out T> {
    data class Success<out T>(val data: T) : MusicResource<T>()
    data class Error(val message: String) : MusicResource<Nothing>()
    object Loading : MusicResource<Nothing>()
}