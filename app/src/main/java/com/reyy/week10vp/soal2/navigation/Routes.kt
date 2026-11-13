package com.reyy.week10vp.soal2.navigation

sealed class Routes(val route: String) {
    object MusicArtist : Routes("music_artist")
    object AlbumDetail : Routes("album_detail/{albumId}") {
        fun createRoute(albumId: String): String = "album_detail/$albumId"
    }
}