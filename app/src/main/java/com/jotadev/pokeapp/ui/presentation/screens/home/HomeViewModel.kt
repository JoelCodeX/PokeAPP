package com.jotadev.pokeapp.ui.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotadev.pokeapp.data.remote.RetrofitClient
import com.jotadev.pokeapp.data.repository.PokemonRepository
import com.jotadev.pokeapp.domain.model.Pokemon
import kotlinx.coroutines.launch
import java.util.Locale

class HomeViewModel : ViewModel() {
    private val repository = PokemonRepository(RetrofitClient.instance)

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val result = repository.getPokemonList(20, 0)
                val pokemonList = result.results.map { dto ->
                    val number = if(dto.url.endsWith("/")) {
                        dto.url.dropLast(1).takeLastWhile { it.isDigit() }
                    } else {
                        dto.url.takeLastWhile { it.isDigit() }
                    }
                    val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$number.png"
                    Pokemon(number.toInt(), dto.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }, url)
                }
                _state.value = _state.value.copy(
                    pokemonList = pokemonList,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}

data class HomeState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
