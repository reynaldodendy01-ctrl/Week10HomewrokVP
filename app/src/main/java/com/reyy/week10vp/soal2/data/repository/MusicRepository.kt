package com.reyy.week10vp.soal2.data.repository

import com.reyy.week10vp.soal2.data.remote.AudioDbApiService
import com.reyy.week10vp.soal2.model.Album
import com.reyy.week10vp.soal2.model.AlbumDetail
import com.reyy.week10vp.soal2.model.ArtistWithAlbums
import com.reyy.week10vp.soal2.util.MusicResource
import com.reyy.week10vp.soal2.util.safeCall
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MusicRepository(private val apiService: AudioDbApiService) {
    private val artistCache = mutableMapOf<String, ArtistWithAlbums>()
    private val albumDetailCache = mutableMapOf<String, AlbumDetail>()

    suspend fun getArtistAndAlbums(artistName: String): MusicResource<ArtistWithAlbums> {
        val query = artistName.trim().ifEmpty { "Coldplay" }
        val cacheKey = query.lowercase()

        artistCache[cacheKey]?.let { cached ->
            return MusicResource.Success(cached)
        }

        return coroutineScope {
            val artistDeferred = async { safeCall { apiService.searchArtist(query) } }
            val albumsDeferred = async { safeCall { apiService.searchAlbums(query) } }

            val artistResult = artistDeferred.await()
            val albumsResult = albumsDeferred.await()

            when (artistResult) {
                is MusicResource.Error -> MusicResource.Error(artistResult.message)
                is MusicResource.Loading -> MusicResource.Loading
                is MusicResource.Success -> {
                    val artistDto = artistResult.data.artists?.firstOrNull()
                        ?: return@coroutineScope MusicResource.Error("Artis \"$query\" tidak ditemukan")

                    val artist = artistDto.toArtist(query)
                    val albumDtos = (albumsResult as? MusicResource.Success)?.data?.album ?: emptyList()
                    val albums = albumDtos.mapIndexed { index, dto ->
                        dto.toAlbum(index = index, fallbackArtist = artist.name, fallbackGenre = artist.genre)
                    }

                    val result = ArtistWithAlbums(artist = artist, albums = albums)
                    artistCache[cacheKey] = result
                    MusicResource.Success(result)
                }
            }
        }
    }

    suspend fun getAlbumDetailWithTracks(albumId: String): MusicResource<AlbumDetail> {
        albumDetailCache[albumId]?.let { cached ->
            return MusicResource.Success(cached)
        }

        return coroutineScope {
            val detailDeferred = async { safeCall { apiService.getAlbumDetail(albumId) } }
            val tracksDeferred = async { safeCall { apiService.getAlbumTracks(albumId) } }

            val detailResult = detailDeferred.await()
            val tracksResult = tracksDeferred.await()

            when (detailResult) {
                is MusicResource.Error -> MusicResource.Error(detailResult.message)
                is MusicResource.Loading -> MusicResource.Loading
                is MusicResource.Success -> {
                    val detailDto = detailResult.data.album?.firstOrNull()
                    val fullAlbum = detailDto?.toAlbum() ?: Album(
                        id = albumId,
                        title = "Detail Album",
                        artistName = "Artist",
                        year = "-",
                        genre = "Music",
                        thumbUrl = "",
                        description = "Deskripsi album belum tersedia."
                    )

                    val trackDtos = (tracksResult as? MusicResource.Success)?.data?.track ?: emptyList()
                    val tracks = trackDtos.mapIndexed { index, dto ->
                        dto.toTrack(index = index, fallbackGenre = fullAlbum.genre)
                    }

                    val result = AlbumDetail(album = fullAlbum, tracks = tracks)
                    albumDetailCache[albumId] = result
                    MusicResource.Success(result)
                }
            }
        }
    }
}