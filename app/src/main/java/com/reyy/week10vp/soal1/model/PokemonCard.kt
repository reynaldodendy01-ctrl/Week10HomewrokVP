package com.reyy.week10vp.soal1.model

data class PokemonCard(
    val id: String,
    val name: String,
    val hp: String,
    val setName: String,
    val number: String,
    val rarity: String,
    val type: String,
    val illustrator: String,
    val imageSmall: String,
    val imageLarge: String,
    val marketPrice: Double,
    val lowestPrice: Double,
    val primaryPrinting: String,
    val activeListings: Int,
    val verifiedSellers: Int
)