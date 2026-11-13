package com.reyy.week10vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.reyy.week10vp.soal1.data.remote.ServiceLocator
import com.reyy.week10vp.soal1.navigation.AppNavHost
import com.reyy.week10vp.soal1.ui.screen.PokeTrackerScreen
import com.reyy.week10vp.soal1.viewmodel.PokemonViewModel
import com.reyy.week10vp.soal2.data.remote.AudioDbApiConfig
import com.reyy.week10vp.soal2.data.repository.MusicRepository
import com.reyy.week10vp.soal2.ui.MusicApp
import com.reyy.week10vp.soal2.viewmodel.MusicViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        runSoal1()
    }

    private fun runSoal1() {
        val viewModel = PokemonViewModel(ServiceLocator.pokemonRepository)

        setContent {
            AppNavHost(viewModel = viewModel)
        }
    }

    private fun runSoal2() {
        val repository = MusicRepository(AudioDbApiConfig.getApiService())
        val viewModel = MusicViewModel(repository)

        setContent {
            MusicApp(viewModel = viewModel)
        }
    }
}