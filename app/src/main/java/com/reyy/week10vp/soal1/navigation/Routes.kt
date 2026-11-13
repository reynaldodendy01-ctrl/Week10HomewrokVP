package com.reyy.week10vp.soal1.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object PokeTracker : Routes
}