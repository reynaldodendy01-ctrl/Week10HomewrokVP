package com.reyy.week10vp.soal2.data.remote

import com.reyy.week10vp.soal2.data.repository.MusicRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceLocator {
    private const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/123/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val audioDbApiService: AudioDbApiService by lazy {
        retrofit.create(AudioDbApiService::class.java)
    }

    val musicRepository: MusicRepository by lazy {
        MusicRepository(audioDbApiService)
    }
}