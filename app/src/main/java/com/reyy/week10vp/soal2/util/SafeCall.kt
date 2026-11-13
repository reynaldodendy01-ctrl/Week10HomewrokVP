package com.reyy.week10vp.soal2.util

import retrofit2.HttpException
import java.io.IOException

sealed class MusicResource<out T> {
    data class Success<out T>(val data: T) : MusicResource<T>()
    data class Error(val message: String) : MusicResource<Nothing>()
    object Loading : MusicResource<Nothing>()
}

suspend fun <T> safeCall(apiCall: suspend () -> T): MusicResource<T> {
    return try {
        MusicResource.Success(apiCall())
    } catch (e: HttpException) {
        MusicResource.Error("HTTP ${e.code()}: Gagal mengambil data dari server")
    } catch (e: IOException) {
        MusicResource.Error(e.localizedMessage ?: "Tidak ada koneksi internet")
    } catch (e: Exception) {
        MusicResource.Error(e.localizedMessage ?: "Terjadi kesalahan tidak terduga")
    }
}