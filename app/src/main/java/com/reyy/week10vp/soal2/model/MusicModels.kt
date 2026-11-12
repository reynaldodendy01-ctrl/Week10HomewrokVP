package com.reyy.week10vp.soal2.model

data class Artist(
    val id: String,
    val name: String,
    val genre: String,
    val origin: String,
    val biography: String,
    val bannerUrl: String,
    val thumbUrl: String
)

data class Album(
    val id: String,
    val title: String,
    val artistName: String,
    val year: String,
    val genre: String,
    val thumbUrl: String,
    val description: String
)

data class Track(
    val id: String,
    val number: String,
    val title: String,
    val genre: String,
    val durationFormatted: String
)

data class ArtistWithAlbums(
    val artist: Artist,
    val albums: List<Album>
)

data class AlbumDetail(
    val album: Album,
    val tracks: List<Track>
)