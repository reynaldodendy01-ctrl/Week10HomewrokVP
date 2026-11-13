package com.reyy.week10vp.soal1.data.repository

import com.reyy.week10vp.soal1.data.remote.PokemonApiService
import com.reyy.week10vp.soal1.model.PokemonCard
import com.reyy.week10vp.soal1.util.Resource
import com.reyy.week10vp.soal1.util.safeCall

class PokemonRepository(private val apiService: PokemonApiService) {
    private val cache = mutableMapOf<String, List<PokemonCard>>()

    suspend fun getCards(pokemonName: String): Resource<List<PokemonCard>> {
        val query = pokemonName.trim().ifEmpty { "Charizard" }
        val cacheKey = query.lowercase()

        cache[cacheKey]?.let { cachedList ->
            return Resource.Success(cachedList)
        }

        return when (val result = safeCall { apiService.searchCards(search = query, limit = 10) }) {
            is Resource.Success -> {
                val dtoList = result.data.data ?: emptyList()
                if (dtoList.isEmpty()) {
                    Resource.Error("Card not found for \"$query\"")
                } else {
                    val mappedCards = dtoList.mapIndexed { index, dto ->
                        dto.toPokemonCard(index)
                    }
                    cache[cacheKey] = mappedCards
                    Resource.Success(mappedCards)
                }
            }
            is Resource.Error -> Resource.Error(result.message)
            is Resource.Loading -> Resource.Loading
        }
    }
}