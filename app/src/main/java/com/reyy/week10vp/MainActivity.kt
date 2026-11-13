package com.reyy.week10vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.reyy.week10vp.soal1.data.remote.ServiceLocator as Soal1ServiceLocator
import com.reyy.week10vp.soal1.navigation.AppNavHost as Soal1AppNavHost
import com.reyy.week10vp.soal1.viewmodel.PokemonViewModel
import com.reyy.week10vp.soal2.data.remote.ServiceLocator as Soal2ServiceLocator
import com.reyy.week10vp.soal2.navigation.AppNavHost as Soal2AppNavHost
import com.reyy.week10vp.soal2.viewmodel.ArtistViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        runSoal2()
    }

    private fun runSoal1() {
        val viewModel = PokemonViewModel(Soal1ServiceLocator.pokemonRepository)

        setContent {
            Soal1AppNavHost(viewModel = viewModel)
        }
    }

    private fun runSoal2() {
        val viewModel = ArtistViewModel(Soal2ServiceLocator.musicRepository)

        setContent {
            Soal2AppNavHost(artistViewModel = viewModel)
        }
    }
}