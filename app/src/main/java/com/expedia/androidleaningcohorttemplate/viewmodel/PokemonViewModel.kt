package com.expedia.androidleaningcohorttemplate.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expedia.androidleaningcohorttemplate.data.api.PokemonService
import com.expedia.androidleaningcohorttemplate.data.model.Pokemon
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

    fun getPokemons(limit: Int? = DEFAULT_LIMIT, offset: Int? = DEFAULT_OFFSET) {
        viewModelScope.launch(Dispatchers.Default) {
            val pokemons = pokemonService.getPokemons(limit, offset)?.toPokemons()
            pokemonList.value = pokemons ?: listOf()
        }
    }

    fun getNextPokemons() {
        viewModelScope.launch(Dispatchers.Default) {
            val pokemons = pokemonService
                .getPokemons(DEFAULT_LIMIT, currentOffset + (DEFAULT_LIMIT * currentPage))
            if (pokemons == null) {
                throw Exception("Error while fetching next pokemons")
            } else {
                currentPage++
                pokemonList.value += pokemons.toPokemons()
            }
        }
    }

    fun getPokemon(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            pokemonService.getPokemon(name)
        }
    }
}