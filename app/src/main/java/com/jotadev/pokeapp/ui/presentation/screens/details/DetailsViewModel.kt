package com.jotadev.pokeapp.ui.presentation.screens.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotadev.pokeapp.data.remote.RetrofitClient
import com.jotadev.pokeapp.data.remote.dto.PokemonDetailDto
import com.jotadev.pokeapp.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val repository = PokemonRepository(RetrofitClient.instance)

    private val _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state

    fun loadPokemonDetail(name: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val result = repository.getPokemonDetail(name.lowercase())
                _state.value = _state.value.copy(
                    pokemonDetail = result,
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

data class DetailsState(
    val pokemonDetail: PokemonDetailDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
