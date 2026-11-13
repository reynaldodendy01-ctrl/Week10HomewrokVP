package com.reyy.week10vp.soal2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.reyy.week10vp.soal2.data.remote.ServiceLocator
import com.reyy.week10vp.soal2.ui.screen.AlbumDetailScreen
import com.reyy.week10vp.soal2.ui.screen.MusicArtistScreen
import com.reyy.week10vp.soal2.viewmodel.AlbumDetailViewModel
import com.reyy.week10vp.soal2.viewmodel.ArtistViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val artistViewModel = remember { ArtistViewModel(ServiceLocator.musicRepository) }

    NavHost(
        navController = navController,
        startDestination = Routes.MusicArtist
    ) {
        composable<Routes.MusicArtist> {
            MusicArtistScreen(
                viewModel = artistViewModel,
                onAlbumClick = { album ->
                    navController.navigate(Routes.AlbumDetail(albumId = album.id))
                }
            )
        }

        composable<Routes.AlbumDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Routes.AlbumDetail>()
            val detailViewModel = remember(route.albumId) {
                AlbumDetailViewModel(ServiceLocator.musicRepository, route.albumId)
            }

            AlbumDetailScreen(
                viewModel = detailViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}