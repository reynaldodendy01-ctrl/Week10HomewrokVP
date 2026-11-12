package com.reyy.week10vp.soal1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyy.week10vp.soal1.data.repository.PokemonRepository
import com.reyy.week10vp.soal1.model.PokemonCard
import com.reyy.week10vp.soal1.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _cardsState = MutableStateFlow<Resource<List<PokemonCard>>>(Resource.Loading)
    val cardsState: StateFlow<Resource<List<PokemonCard>>> = _cardsState

    private val _searchQuery = MutableStateFlow("Charizard")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCard = MutableStateFlow<PokemonCard?>(null)
    val selectedCard: StateFlow<PokemonCard?> = _selectedCard

    init {
        searchCards("Charizard")
    }

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun searchCards(query: String = _searchQuery.value) {
        _searchQuery.value = query
        viewModelScope.launch {
            _cardsState.value = Resource.Loading
            _cardsState.value = repository.getCards(query)
        }
    }

    fun selectCard(card: PokemonCard?) {
        _selectedCard.value = card
    }
}