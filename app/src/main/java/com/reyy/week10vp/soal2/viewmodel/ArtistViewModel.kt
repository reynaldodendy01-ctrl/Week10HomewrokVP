package com.reyy.week10vp.soal2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyy.week10vp.soal2.data.repository.MusicRepository
import com.reyy.week10vp.soal2.model.ArtistWithAlbums
import com.reyy.week10vp.soal2.util.MusicResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _artistState = MutableStateFlow<MusicResource<ArtistWithAlbums>>(MusicResource.Loading)
    val artistState: StateFlow<MusicResource<ArtistWithAlbums>> = _artistState

    private val _searchQuery = MutableStateFlow("Coldplay")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        searchArtist("Coldplay")
    }

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun searchArtist(query: String = _searchQuery.value) {
        _searchQuery.value = query
        viewModelScope.launch {
            _artistState.value = MusicResource.Loading
            _artistState.value = repository.getArtistAndAlbums(query)
        }
    }
}