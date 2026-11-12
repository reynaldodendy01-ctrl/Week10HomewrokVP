package com.reyy.week10vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.reyy.week10vp.soal1.data.remote.PokemonApiConfig
import com.reyy.week10vp.soal1.data.repository.PokemonRepository
import com.reyy.week10vp.soal2.data.remote.AudioDbApiConfig
import com.reyy.week10vp.soal2.data.repository.MusicRepository
import com.reyy.week10vp.soal2.ui.MusicApp
import com.reyy.week10vp.soal2.viewmodel.MusicViewModel
import com.reyy.week10vp.soal1.ui.screen.PokeTrackerScreen
import com.reyy.week10vp.soal1.viewmodel.PokemonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        runSoal2()
//        runSoal1()
    }

    private fun runSoal1() {
        val repository = PokemonRepository(PokemonApiConfig.getApiService())
        val viewModel = PokemonViewModel(repository)

        setContent {
            PokeTrackerScreen(viewModel = viewModel)
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