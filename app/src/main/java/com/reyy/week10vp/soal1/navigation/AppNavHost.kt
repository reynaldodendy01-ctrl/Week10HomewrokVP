package com.reyy.week10vp.soal1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reyy.week10vp.soal1.data.remote.ServiceLocator
import com.reyy.week10vp.soal1.ui.screen.PokeTrackerScreen
import com.reyy.week10vp.soal1.viewmodel.PokemonViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel = remember { PokemonViewModel(ServiceLocator.pokemonRepository) }

    NavHost(
        navController = navController,
        startDestination = Routes.PokeTracker
    ) {
        composable<Routes.PokeTracker> {
            PokeTrackerScreen(viewModel = viewModel)
        }
    }
}