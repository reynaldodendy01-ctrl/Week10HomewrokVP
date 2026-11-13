package com.reyy.week10vp.soal1.util

import retrofit2.HttpException
import java.io.IOException

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

suspend fun <T> safeCall(apiCall: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(apiCall())
    } catch (e: HttpException) {
        val errorMsg = if (e.code() == 429) {
            "HTTP 429 (API Quota Exceeded)"
        } else {
            "HTTP ${e.code()}"
        }
        Resource.Error(errorMsg)
    } catch (e: IOException) {
        Resource.Error("Network Error / No Internet")
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Unknown Error")
    }
}