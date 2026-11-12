package com.reyy.week10vp.soal1.data.repository

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.reyy.week10vp.soal1.data.remote.PokemonApiService
import com.reyy.week10vp.soal1.model.PokemonCard
import com.reyy.week10vp.soal1.util.Resource
import retrofit2.HttpException
import java.io.IOException

class PokemonRepository(private val apiService: PokemonApiService) {
    suspend fun getCards(pokemonName: String): Resource<List<PokemonCard>> {
        return try {
            val query = pokemonName.trim().ifEmpty { "Charizard" }
            val response = apiService.searchCards(search = query, limit = 20)
            val dtoList = response.data ?: emptyList()

            if (dtoList.isEmpty()) {
                return Resource.Error("Card not found for \"$query\"")
            }

            val mappedCards = dtoList.mapIndexed { index, dto ->
                val rawName = dto.name ?: "Unknown Card"
                val number = dto.cardNumber ?: rawName.substringAfterLast("- ", "").ifEmpty { "-" }
                val cleanName = rawName.substringBeforeLast(" - ").ifEmpty { rawName }

                val setName = extractSetName(dto.setInfo)
                val typeName = extractTypeName(dto.cardType)
                val (smallImg, largeImg) = extractImages(dto.imageInfo)
                val priceData = extractPriceData(dto.pricesInfo)

                PokemonCard(
                    id = dto.id ?: "${cleanName}_${number}_$index",
                    name = cleanName,
                    hp = "${dto.hp ?: "180"} HP",
                    setName = setName,
                    number = number,
                    rarity = dto.rarity ?: "Promo",
                    type = typeName,
                    illustrator = dto.artist ?: "Unknown Artist",
                    imageSmall = smallImg,
                    imageLarge = largeImg,
                    marketPrice = priceData.market,
                    lowestPrice = priceData.lowest,
                    primaryPrinting = priceData.printing,
                    activeListings = priceData.listings,
                    verifiedSellers = priceData.sellers
                )
            }
            Resource.Success(mappedCards)
        } catch (e: HttpException) {
            Resource.Error("HTTP ${e.code()}")
        } catch (e: IOException) {
            Resource.Error("Network Error / No Internet")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown Error")
        }
    }

    private fun extractSetName(element: JsonElement?): String {
        if (element == null || element.isJsonNull) return "Unknown Set"
        if (element.isJsonPrimitive) return element.asString
        if (element.isJsonObject) {
            return element.asJsonObject.get("name")?.asString ?: "Unknown Set"
        }
        return "Unknown Set"
    }

    private fun extractTypeName(element: JsonElement?): String {
        if (element == null || element.isJsonNull) return "Colorless"
        if (element.isJsonPrimitive) return element.asString
        if (element.isJsonArray && element.asJsonArray.size() > 0) {
            return element.asJsonArray[0].asString
        }
        return "Colorless"
    }

    private fun extractImages(element: JsonElement?): Pair<String, String> {
        if (element == null || element.isJsonNull) return "" to ""
        if (element.isJsonPrimitive) {
            val url = element.asString
            return url to url
        }
        if (element.isJsonObject) {
            val obj = element.asJsonObject
            val small = obj.get("small")?.asString ?: obj.get("url")?.asString ?: ""
            val large = obj.get("large")?.asString ?: small
            return small to large
        }
        return "" to ""
    }

    private data class ExtractedPrice(
        val market: Double,
        val lowest: Double,
        val printing: String,
        val listings: Int,
        val sellers: Int
    )

    private fun extractPriceData(element: JsonElement?): ExtractedPrice {
        if (element == null || !element.isJsonObject) {
            return ExtractedPrice(0.0, 0.0, "Normal", 0, 0)
        }
        val root = element.asJsonObject
        val targetObj = findPriceObject(root)

        val market = targetObj.getDoubleOrNull("market")
            ?: targetObj.getDoubleOrNull("marketPrice")
            ?: targetObj.getDoubleOrNull("price")
            ?: 0.0

        val lowest = targetObj.getDoubleOrNull("low")
            ?: targetObj.getDoubleOrNull("lowest")
            ?: targetObj.getDoubleOrNull("lowestPrice")
            ?: market

        val printing = targetObj.getStringOrNull("primaryPrinting")
            ?: targetObj.getStringOrNull("printing")
            ?: "Holofoil"

        val listings = targetObj.getIntOrNull("listings")
            ?: targetObj.getIntOrNull("activeListings")
            ?: 0

        val sellers = targetObj.getIntOrNull("sellers")
            ?: targetObj.getIntOrNull("verifiedSellers")
            ?: 0

        return ExtractedPrice(market, lowest, printing, listings, sellers)
    }

    private fun findPriceObject(root: JsonObject): JsonObject {
        if (root.has("market") || root.has("marketPrice") || root.has("price")) return root
        val nestedKeys = listOf("conditions", "Near Mint", "holofoil", "normal", "reverseHolofoil", "prices", "tcgPlayer", "tcgplayer")
        for (key in nestedKeys) {
            val child = root.get(key)
            if (child != null && child.isJsonObject) {
                return findPriceObject(child.asJsonObject)
            }
        }
        return root
    }

    private fun JsonObject.getDoubleOrNull(key: String): Double? {
        val el = get(key) ?: return null
        return if (el.isJsonPrimitive && el.asJsonPrimitive.isNumber) el.asDouble else null
    }

    private fun JsonObject.getIntOrNull(key: String): Int? {
        val el = get(key) ?: return null
        return if (el.isJsonPrimitive && el.asJsonPrimitive.isNumber) el.asInt else null
    }

    private fun JsonObject.getStringOrNull(key: String): String? {
        val el = get(key) ?: return null
        return if (el.isJsonPrimitive && el.asJsonPrimitive.isString) el.asString else null
    }
}