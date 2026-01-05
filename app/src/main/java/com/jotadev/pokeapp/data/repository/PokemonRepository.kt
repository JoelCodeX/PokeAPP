package com.jotadev.pokeapp.data.repository

import com.jotadev.pokeapp.data.remote.PokeApi
import com.jotadev.pokeapp.data.remote.dto.PokemonDetailDto
import com.jotadev.pokeapp.data.remote.dto.PokemonListResponse

class PokemonRepository(private val api: PokeApi) {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        return api.getPokemonList(limit, offset)
    }

    suspend fun getPokemonDetail(name: String): PokemonDetailDto {
        return api.getPokemonDetail(name)
    }
}
