package com.reyy.week10vp.soal2.data.repository

import com.reyy.week10vp.soal2.data.remote.AudioDbApiService
import com.reyy.week10vp.soal2.model.Album
import com.reyy.week10vp.soal2.model.AlbumDetail
import com.reyy.week10vp.soal2.model.Artist
import com.reyy.week10vp.soal2.model.ArtistWithAlbums
import com.reyy.week10vp.soal2.model.Track
import com.reyy.week10vp.soal2.util.MusicResource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class MusicRepository(private val apiService: AudioDbApiService) {
    private val artistCache = mutableMapOf<String, ArtistWithAlbums>()
    private val albumDetailCache = mutableMapOf<String, AlbumDetail>()

    suspend fun getArtistAndAlbums(artistName: String): MusicResource<ArtistWithAlbums> {
        val query = artistName.trim().ifEmpty { "Coldplay" }
        val cacheKey = query.lowercase()

        artistCache[cacheKey]?.let { cached ->
            return MusicResource.Success(cached)
        }

        return try {
            coroutineScope {
                val artistDeferred = async { apiService.searchArtist(query) }
                val albumsDeferred = async { apiService.searchAlbums(query) }

                val artistDto = artistDeferred.await().artists?.firstOrNull()
                    ?: return@coroutineScope MusicResource.Error("Artis \"$query\" tidak ditemukan")

                val albumDtos = albumsDeferred.await().album ?: emptyList()

                val artist = Artist(
                    id = artistDto.idArtist ?: "",
                    name = artistDto.strArtist ?: query,
                    genre = artistDto.strGenre?.takeIf { it.isNotBlank() } ?: "Music",
                    origin = artistDto.strCountry?.takeIf { it.isNotBlank() } ?: "Unknown Origin",
                    biography = artistDto.strBiographyEN?.takeIf { it.isNotBlank() }
                        ?: "Biografi artis belum tersedia.",
                    bannerUrl = artistDto.strArtistBanner?.takeIf { it.isNotBlank() }
                        ?: artistDto.strArtistFanart?.takeIf { it.isNotBlank() }
                        ?: artistDto.strArtistThumb.orEmpty(),
                    thumbUrl = artistDto.strArtistThumb.orEmpty()
                )

                val albums = albumDtos.mapIndexed { index, dto ->
                    Album(
                        id = dto.idAlbum ?: "album_$index",
                        title = dto.strAlbum ?: "Unknown Album",
                        artistName = dto.strArtist ?: artist.name,
                        year = dto.intYearReleased?.takeIf { it.isNotBlank() } ?: "-",
                        genre = dto.strGenre?.takeIf { it.isNotBlank() } ?: artist.genre,
                        thumbUrl = dto.strAlbumThumb.orEmpty(),
                        description = dto.strDescriptionEN?.takeIf { it.isNotBlank() }
                            ?: "Deskripsi album belum tersedia."
                    )
                }

                val result = ArtistWithAlbums(artist = artist, albums = albums)
                artistCache[cacheKey] = result
                MusicResource.Success(result)
            }
        } catch (e: HttpException) {
            MusicResource.Error("HTTP ${e.code()}: Gagal mengambil data dari server")
        } catch (e: IOException) {
            MusicResource.Error(e.localizedMessage ?: "Tidak ada koneksi internet")
        } catch (e: Exception) {
            MusicResource.Error(e.localizedMessage ?: "Terjadi kesalahan tidak terduga")
        }
    }

    suspend fun getAlbumDetailWithTracks(fallbackAlbum: Album): MusicResource<AlbumDetail> {
        albumDetailCache[fallbackAlbum.id]?.let { cached ->
            return MusicResource.Success(cached)
        }

        return try {
            coroutineScope {
                val detailDeferred = async { apiService.getAlbumDetail(fallbackAlbum.id) }
                val tracksDeferred = async { apiService.getAlbumTracks(fallbackAlbum.id) }

                val detailDto = detailDeferred.await().album?.firstOrNull()
                val trackDtos = tracksDeferred.await().track ?: emptyList()

                val fullAlbum = Album(
                    id = detailDto?.idAlbum ?: fallbackAlbum.id,
                    title = detailDto?.strAlbum ?: fallbackAlbum.title,
                    artistName = detailDto?.strArtist ?: fallbackAlbum.artistName,
                    year = detailDto?.intYearReleased?.takeIf { it.isNotBlank() } ?: fallbackAlbum.year,
                    genre = detailDto?.strGenre?.takeIf { it.isNotBlank() } ?: fallbackAlbum.genre,
                    thumbUrl = detailDto?.strAlbumThumb?.takeIf { it.isNotBlank() } ?: fallbackAlbum.thumbUrl,
                    description = detailDto?.strDescriptionEN?.takeIf { it.isNotBlank() }
                        ?: fallbackAlbum.description
                )

                val tracks = trackDtos.mapIndexed { index, dto ->
                    val rawNumber = dto.intTrackNumber?.toIntOrNull() ?: (index + 1)
                    val formattedNumber = String.format(Locale.US, "%02d", rawNumber)

                    Track(
                        id = dto.idTrack ?: "track_$index",
                        number = formattedNumber,
                        title = dto.strTrack ?: "Track ${index + 1}",
                        genre = dto.strGenre?.takeIf { it.isNotBlank() } ?: fullAlbum.genre,
                        durationFormatted = formatDuration(dto.intDuration)
                    )
                }

                val result = AlbumDetail(album = fullAlbum, tracks = tracks)
                albumDetailCache[fallbackAlbum.id] = result
                MusicResource.Success(result)
            }
        } catch (e: HttpException) {
            MusicResource.Error("HTTP ${e.code()}: Gagal memuat detail album")
        } catch (e: IOException) {
            MusicResource.Error(e.localizedMessage ?: "Tidak ada koneksi internet")
        } catch (e: Exception) {
            MusicResource.Error(e.localizedMessage ?: "Terjadi kesalahan saat memuat track")
        }
    }

    private fun formatDuration(durationMsString: String?): String {
        val totalMs = durationMsString?.toLongOrNull() ?: return "00:00"
        val totalSeconds = totalMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.US, "%02d:%02d", minutes, seconds)
    }
}