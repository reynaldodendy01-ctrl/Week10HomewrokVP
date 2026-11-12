package com.reyy.week10vp.soal2.data.remote

import com.reyy.week10vp.soal2.data.dto.AlbumResponseDto
import com.reyy.week10vp.soal2.data.dto.ArtistResponseDto
import com.reyy.week10vp.soal2.data.dto.TrackResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AudioDbApiService {
    @GET("search.php")
    suspend fun searchArtist(
        @Query("s") artistName: String
    ): ArtistResponseDto

    @GET("searchalbum.php")
    suspend fun searchAlbums(
        @Query("s") artistName: String
    ): AlbumResponseDto

    @GET("album.php")
    suspend fun getAlbumDetail(
        @Query("m") albumId: String
    ): AlbumResponseDto

    @GET("track.php")
    suspend fun getAlbumTracks(
        @Query("m") albumId: String
    ): TrackResponseDto
}