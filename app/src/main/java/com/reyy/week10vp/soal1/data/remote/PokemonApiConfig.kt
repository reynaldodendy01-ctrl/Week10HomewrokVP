package com.reyy.week10vp.soal1.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object PokemonApiConfig {
    private const val BASE_URL = "https://www.pokemonpricetracker.com/api/v2/"
//    private const val API_KEY = "pokeprice_free_872228d2a8e95bf268f039ab5da18f95184d1f0d22a9dc86"
    // const val API_KEY = "pokeprice_free_46a78af0b7d80dd053ae8b8f58c43e0caa6457f299c7188f"

    private const val API_KEY = "pokeprice_free_2e0920641ba36ea2f6340386c57ee0cca2488c892ddd9fd7"
    fun getApiService(): PokemonApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PokemonApiService::class.java)
    }
}