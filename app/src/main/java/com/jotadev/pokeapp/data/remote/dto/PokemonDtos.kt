package com.jotadev.pokeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>
)

data class PokemonDto(
    val name: String,
    val url: String
)

data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<TypeResponse>,
    val stats: List<StatResponse>,
    val abilities: List<AbilityResponse>
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
    val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?
)

data class TypeResponse(
    val slot: Int,
    val type: TypeDto
)

data class TypeDto(
    val name: String,
    val url: String
)

data class StatResponse(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: StatDto
)

data class StatDto(
    val name: String
)

data class AbilityResponse(
    val ability: AbilityDto,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int
)

data class AbilityDto(
    val name: String,
    val url: String
)
