package com.reyy.week10vp.soal1.data.dto

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class PokemonResponseDto(
    @SerializedName(value = "data", alternate = ["cards", "results"])
    val data: List<PokemonCardDto>?
)

data class PokemonCardDto(
    @SerializedName(value = "id", alternate = ["_id", "tcgPlayerId"])
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("hp")
    val hp: String?,
    @SerializedName(value = "cardNumber", alternate = ["number"])
    val cardNumber: String?,
    @SerializedName(value = "setName", alternate = ["set"])
    val setInfo: JsonElement?,
    @SerializedName("rarity")
    val rarity: String?,
    @SerializedName(value = "cardType", alternate = ["type", "types"])
    val cardType: JsonElement?,
    @SerializedName(value = "artist", alternate = ["illustrator"])
    val artist: String?,
    @SerializedName(value = "imageUrl", alternate = ["image", "images"])
    val imageInfo: JsonElement?,
    @SerializedName(value = "prices", alternate = ["tcgplayer", "tcgPlayer"])
    val pricesInfo: JsonElement?
)