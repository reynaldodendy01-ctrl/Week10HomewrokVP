package com.reyy.week10vp.soal2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyy.week10vp.soal2.data.repository.MusicRepository
import com.reyy.week10vp.soal2.model.AlbumDetail
import com.reyy.week10vp.soal2.util.MusicResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val repository: MusicRepository,
    private val albumId: String
) : ViewModel() {

    private val _albumDetailState = MutableStateFlow<MusicResource<AlbumDetail>>(MusicResource.Loading)
    val albumDetailState: StateFlow<MusicResource<AlbumDetail>> = _albumDetailState

    init {
        loadAlbumDetail()
    }

    fun loadAlbumDetail() {
        viewModelScope.launch {
            _albumDetailState.value = MusicResource.Loading
            _albumDetailState.value = repository.getAlbumDetailWithTracks(albumId)
        }
    }
}