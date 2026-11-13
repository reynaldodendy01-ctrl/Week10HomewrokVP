package com.reyy.week10vp.soal2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.reyy.week10vp.soal2.data.remote.ServiceLocator
import com.reyy.week10vp.soal2.ui.screen.AlbumDetailScreen
import com.reyy.week10vp.soal2.ui.screen.MusicArtistScreen
import com.reyy.week10vp.soal2.viewmodel.AlbumDetailViewModel
import com.reyy.week10vp.soal2.viewmodel.ArtistViewModel

@Composable
fun AppNavHost(artistViewModel: ArtistViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MusicArtist.route
    ) {
        composable(Routes.MusicArtist.route) {
            MusicArtistScreen(
                viewModel = artistViewModel,
                onAlbumClick = { album ->
                    navController.navigate(Routes.AlbumDetail.createRoute(album.id))
                }
            )
        }

        composable(
            route = Routes.AlbumDetail.route,
            arguments = listOf(navArgument("albumId") { type = NavType.StringType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId").orEmpty()
            val detailViewModel = remember(albumId) {
                AlbumDetailViewModel(ServiceLocator.musicRepository, albumId)
            }

            AlbumDetailScreen(
                viewModel = detailViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}