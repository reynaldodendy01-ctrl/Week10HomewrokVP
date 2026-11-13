package com.reyy.week10vp.soal2.data.dto

import com.google.gson.annotations.SerializedName

data class ArtistResponseDto(
    @SerializedName("artists") val artists: List<ArtistDto>?
)

data class ArtistDto(
    @SerializedName("idArtist") val idArtist: String?,
    @SerializedName("strArtist") val strArtist: String?,
    @SerializedName("strGenre") val strGenre: String?,
    @SerializedName("strCountry") val strCountry: String?,
    @SerializedName(value = "strBiography", alternate = ["strBiographyEN"]) val strBiography: String?,
    @SerializedName("strArtistBanner") val strArtistBanner: String?,
    @SerializedName("strArtistThumb") val strArtistThumb: String?,
    @SerializedName("strArtistFanart") val strArtistFanart: String?
)

data class AlbumResponseDto(
    @SerializedName("album") val album: List<AlbumDto>?
)

data class AlbumDto(
    @SerializedName("idAlbum") val idAlbum: String?,
    @SerializedName("idArtist") val idArtist: String?,
    @SerializedName("strAlbum") val strAlbum: String?,
    @SerializedName("strArtist") val strArtist: String?,
    @SerializedName("intYearReleased") val intYearReleased: String?,
    @SerializedName("strGenre") val strGenre: String?,
    @SerializedName("strAlbumThumb") val strAlbumThumb: String?,
    @SerializedName(value = "strDescription", alternate = ["strDescriptionEN"]) val strDescription: String?
)

data class TrackResponseDto(
    @SerializedName("track") val track: List<TrackDto>?
)

data class TrackDto(
    @SerializedName("idTrack") val idTrack: String?,
    @SerializedName("idAlbum") val idAlbum: String?,
    @SerializedName("strTrack") val strTrack: String?,
    @SerializedName("strGenre") val strGenre: String?,
    @SerializedName("intDuration") val intDuration: String?,
    @SerializedName("intTrackNumber") val intTrackNumber: String?
)