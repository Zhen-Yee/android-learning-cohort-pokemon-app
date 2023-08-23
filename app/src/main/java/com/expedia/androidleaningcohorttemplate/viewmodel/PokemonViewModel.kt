package com.expedia.androidleaningcohorttemplate.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expedia.androidleaningcohorttemplate.data.api.PokemonService
import com.expedia.androidleaningcohorttemplate.data.model.Pokemon
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDetailsDTO
import com.expedia.androidleaningcohorttemplate.util.DEFAULT_LIMIT
import com.expedia.androidleaningcohorttemplate.util.DEFAULT_OFFSET
import com.expedia.androidleaningcohorttemplate.util.toPokemons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonService: PokemonService
): ViewModel() {

    var pokemonList = mutableStateOf<List<Pokemon>>(listOf())
    var currentPage = 1
    val currentOffset = DEFAULT_OFFSET
    var pokemonDetailsMap = mutableStateOf(mutableMapOf<String, PokemonDetailsDTO>())

    fun getPokemons(limit: Int? = DEFAULT_LIMIT, offset: Int? = DEFAULT_OFFSET) {
        viewModelScope.launch(Dispatchers.Default) {
            val pokemons = pokemonService.getPokemons(limit, offset)?.toPokemons()
            pokemonList.value = pokemons ?: listOf()
        }
    }

    fun getNextPokemons() {
        viewModelScope.launch(Dispatchers.Default) {
            val pokemons = pokemonService.getPokemons(
                limit = DEFAULT_LIMIT,
                offset = currentOffset + (DEFAULT_LIMIT * currentPage)
            )

            if (pokemons == null) {
                throw Exception("Error while fetching next pokemons")
            } else {
                currentPage++
                pokemonList.value += pokemons.toPokemons()
            }
        }
    }

    fun getPokemonDetailsById(id: String) {
        if (!pokemonDetailsMap.value.contains(id)) {
            viewModelScope.launch(Dispatchers.Default) {
                val pokemonDetails = pokemonService.getPokemonDetailsById(id)

                if (pokemonDetails == null) {
                    throw Exception("Error while fetching pokemon details")
                } else {
                    pokemonDetailsMap.value[id] = pokemonDetails
                }
            }
        }
    }
}