package com.reyy.week10vp.soal2.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object MusicArtist : Routes

    @Serializable
    data class AlbumDetail(val albumId: String) : Routes
}