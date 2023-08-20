package com.expedia.androidleaningcohorttemplate.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expedia.androidleaningcohorttemplate.data.api.PokemonService
import com.expedia.androidleaningcohorttemplate.data.model.Pokemon
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

    fun getPokemons() {
        viewModelScope.launch(Dispatchers.Default) {
            val pokemons = pokemonService.getPokemons()?.toPokemons()
            pokemonList.value = pokemons ?: listOf()
        }
    }

    fun getPokemon(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            pokemonService.getPokemon(name)
        }
    }
}