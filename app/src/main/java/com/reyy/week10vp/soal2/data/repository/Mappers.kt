package com.reyy.week10vp.soal2.data.repository

import com.reyy.week10vp.soal2.data.dto.AlbumDto
import com.reyy.week10vp.soal2.data.dto.ArtistDto
import com.reyy.week10vp.soal2.data.dto.TrackDto
import com.reyy.week10vp.soal2.model.Album
import com.reyy.week10vp.soal2.model.Artist
import com.reyy.week10vp.soal2.model.Track
import java.util.Locale

fun ArtistDto.toArtist(fallbackName: String): Artist {
    return Artist(
        id = idArtist.orEmpty(),
        name = strArtist?.takeIf { it.isNotBlank() } ?: fallbackName,
        genre = strGenre?.takeIf { it.isNotBlank() } ?: "Music",
        origin = strCountry?.takeIf { it.isNotBlank() } ?: "Unknown Origin",
        biography = strBiography?.takeIf { it.isNotBlank() } ?: "Biografi artis belum tersedia.",
        bannerUrl = strArtistBanner?.takeIf { it.isNotBlank() }
            ?: strArtistFanart?.takeIf { it.isNotBlank() }
            ?: strArtistThumb.orEmpty(),
        thumbUrl = strArtistThumb.orEmpty()
    )
}

fun AlbumDto.toAlbum(index: Int = 0, fallbackArtist: String = "", fallbackGenre: String = "Music"): Album {
    return Album(
        id = idAlbum ?: "album_$index",
        title = strAlbum?.takeIf { it.isNotBlank() } ?: "Unknown Album",
        artistName = strArtist?.takeIf { it.isNotBlank() } ?: fallbackArtist,
        year = intYearReleased?.takeIf { it.isNotBlank() } ?: "-",
        genre = strGenre?.takeIf { it.isNotBlank() } ?: fallbackGenre,
        thumbUrl = strAlbumThumb.orEmpty(),
        description = strDescription?.takeIf { it.isNotBlank() } ?: "Deskripsi album belum tersedia."
    )
}

fun TrackDto.toTrack(index: Int, fallbackGenre: String): Track {
    val rawNumber = intTrackNumber?.toIntOrNull() ?: (index + 1)
    val formattedNumber = String.format(Locale.US, "%02d", rawNumber)

    return Track(
        id = idTrack ?: "track_$index",
        number = formattedNumber,
        title = strTrack?.takeIf { it.isNotBlank() } ?: "Track ${index + 1}",
        genre = strGenre?.takeIf { it.isNotBlank() } ?: fallbackGenre,
        durationFormatted = formatDuration(intDuration)
    )
}

private fun formatDuration(durationMsString: String?): String {
    val totalMs = durationMsString?.toLongOrNull() ?: return "00:00"
    val totalSeconds = totalMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.US, "%02d:%02d", minutes, seconds)
}