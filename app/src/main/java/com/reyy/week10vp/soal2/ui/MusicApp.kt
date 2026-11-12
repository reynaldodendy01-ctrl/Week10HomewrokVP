package com.reyy.week10vp.soal2.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.reyy.week10vp.soal2.ui.screen.AlbumDetailScreen
import com.reyy.week10vp.soal2.ui.screen.MusicArtistScreen
import com.reyy.week10vp.soal2.viewmodel.MusicViewModel

@Composable
fun MusicApp(viewModel: MusicViewModel) {
    val selectedAlbum by viewModel.selectedAlbum.collectAsState()

    val currentAlbum = selectedAlbum
    if (currentAlbum != null) {
        BackHandler {
            viewModel.closeAlbumDetail()
        }
        AlbumDetailScreen(
            album = currentAlbum,
            viewModel = viewModel,
            onBack = { viewModel.closeAlbumDetail() }
        )
    } else {
        MusicArtistScreen(
            viewModel = viewModel,
            onAlbumClick = { album -> viewModel.openAlbumDetail(album) }
        )
    }
}