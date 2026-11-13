package com.reyy.week10vp.soal1.navigation

sealed class Routes(val route: String) {
    object PokeTracker : Routes("poke_tracker")
}