package com.reyy.week10vp.soal1.data.remote

import com.reyy.week10vp.soal1.data.dto.PokemonResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET("cards")
    suspend fun searchCards(
        @Query("search") search: String,
        @Query("limit") limit: Int = 20
    ): PokemonResponseDto
}