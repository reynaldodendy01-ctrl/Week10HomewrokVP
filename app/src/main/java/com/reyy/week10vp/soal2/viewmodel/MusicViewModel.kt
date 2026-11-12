package com.reyy.week10vp.soal2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyy.week10vp.soal2.data.repository.MusicRepository
import com.reyy.week10vp.soal2.model.Album
import com.reyy.week10vp.soal2.model.AlbumDetail
import com.reyy.week10vp.soal2.model.ArtistWithAlbums
import com.reyy.week10vp.soal2.util.MusicResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MusicViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _artistState = MutableStateFlow<MusicResource<ArtistWithAlbums>>(MusicResource.Loading)
    val artistState: StateFlow<MusicResource<ArtistWithAlbums>> = _artistState

    private val _searchQuery = MutableStateFlow("Coldplay")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedAlbum = MutableStateFlow<Album?>(null)
    val selectedAlbum: StateFlow<Album?> = _selectedAlbum

    private val _albumDetailState = MutableStateFlow<MusicResource<AlbumDetail>>(MusicResource.Loading)
    val albumDetailState: StateFlow<MusicResource<AlbumDetail>> = _albumDetailState

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

    fun openAlbumDetail(album: Album) {
        _selectedAlbum.value = album
        viewModelScope.launch {
            _albumDetailState.value = MusicResource.Loading
            _albumDetailState.value = repository.getAlbumDetailWithTracks(album)
        }
    }

    fun closeAlbumDetail() {
        _selectedAlbum.value = null
    }
}