package com.expedia.androidleaningcohorttemplate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expedia.androidleaningcohorttemplate.data.api.PokemonService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonService: PokemonService
): ViewModel() {

    fun getPokemons() {
        viewModelScope.launch(Dispatchers.Default) {
            pokemonService.getPokemons()
        }
    }
}