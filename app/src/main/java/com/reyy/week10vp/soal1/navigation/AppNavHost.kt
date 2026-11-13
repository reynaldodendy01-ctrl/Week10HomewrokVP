package com.reyy.week10vp.soal1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reyy.week10vp.soal1.ui.screen.PokeTrackerScreen
import com.reyy.week10vp.soal1.viewmodel.PokemonViewModel

@Composable
fun AppNavHost(viewModel: PokemonViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.PokeTracker.route
    ) {
        composable(Routes.PokeTracker.route) {
            PokeTrackerScreen(viewModel = viewModel)
        }
    }
}