package com.reyy.week10vp.soal1.data.remote

import com.reyy.week10vp.soal1.data.repository.PokemonRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceLocator {
    private const val BASE_URL = "https://www.pokemonpricetracker.com/api/v2/"
    private const val API_KEY = "pokeprice_free_872228d2a8e95bf268f039ab5da18f95184d1f0d22a9dc86"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(API_KEY))
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokemonApiService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

    val pokemonRepository: PokemonRepository by lazy {
        PokemonRepository(pokemonApiService)
    }
}