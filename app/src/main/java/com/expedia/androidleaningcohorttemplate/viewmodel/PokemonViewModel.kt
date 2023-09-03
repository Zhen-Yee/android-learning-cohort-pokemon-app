package com.expedia.androidleaningcohorttemplate.viewmodel

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.expedia.androidleaningcohorttemplate.data.api.PokemonService
import com.expedia.androidleaningcohorttemplate.data.model.Pokemon
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDetailsDTO
import com.expedia.androidleaningcohorttemplate.util.DEFAULT_LIMIT
import com.expedia.androidleaningcohorttemplate.util.DEFAULT_OFFSET
import com.expedia.androidleaningcohorttemplate.util.toPokemons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonService: PokemonService
): ViewModel() {

    val pokemonList = mutableStateOf<List<Pokemon>>(listOf())
    val pokemonDetailsMap = mutableStateOf(mutableMapOf<String, PokemonDetailsDTO>())
    val pokemonGradientMap = mutableStateOf(mutableMapOf<String, List<Color>>())
    val isLoading = mutableStateOf(false)

    // Unused - Flow
//    private val pokemonListStateFlow = MutableStateFlow<List<Pokemon>>(listOf())
//    val pokemonListFlow = pokemonListStateFlow.asStateFlow()

    private var currentPage = 1
    private val currentOffset = DEFAULT_OFFSET

    fun getPokemons(limit: Int? = DEFAULT_LIMIT, offset: Int? = DEFAULT_OFFSET) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.value = true
                val pokemons = pokemonService.getPokemons(limit, offset)?.toPokemons()
                if (pokemons != null) {
                    pokemonList.value = pokemons
                } else {
                    throw Exception("Error while fetching pokemons")
                }
                isLoading.value = false
            } catch (e: Exception) {
                Log.d("GetPokemons", "Error: $e")
                isLoading.value = false
            }
        }
    }

    fun getNextPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pokemons = pokemonService.getPokemons(
                    limit = DEFAULT_LIMIT,
                    offset = currentOffset + (DEFAULT_LIMIT * currentPage)
                )

                if (pokemons != null) {
                    currentPage++
                    pokemonList.value += pokemons.toPokemons()
                } else {
                    throw Exception("Error while fetching next pokemons")
                }
            } catch (e: Exception) {
                Log.d("GetNextPokemons", "Error: $e")
            }
        }
    }

//    fun getNextPokemonsFlow() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val pokemons = pokemonService.getPokemons(
//                    limit = DEFAULT_LIMIT,
//                    offset = currentOffset + (DEFAULT_LIMIT * currentPage)
//                )
//
//                if (pokemons != null) {
//                    flow {
//                        emit(pokemons.toPokemons())
//                    }.collect {
//                        currentPage++
//                        pokemonListStateFlow.value += it
//                    }
//                } else {
//                    throw Exception("Error while fetching next pokemons")
//                }
//            } catch (e: Exception) {
//                Log.d("GetNextPokemons", "Error: $e")
//            }
//        }
//    }

    fun getPokemonDetailsById(id: String) {
        if (!pokemonDetailsMap.value.contains(id)) {
            viewModelScope.launch(Dispatchers.IO) {
                isLoading.value = true
                val pokemonGradient = async { extractPrimaryColorsFromImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png") }
                val pokemonDetails = pokemonService.getPokemonDetailsById(id)

                try {
                    if (pokemonDetails != null) {
                        pokemonDetailsMap.value[id] = pokemonDetails
                        pokemonGradientMap.value[id] = pokemonGradient.await()
                        isLoading.value = false
                    } else {
                        throw Exception("Error while fetching pokemon details")
                    }
                } catch(e: Exception) {
                    Log.d("GetPokemonDetailsById", "Error: $e")
                    isLoading.value = false
                }
            }
        }
    }

    private suspend fun extractPrimaryColorsFromImageUrl(imageUrl: String): List<Color> = withContext(Dispatchers.IO) {
        val inputStream = URL(imageUrl).openStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val palette = Palette.from(bitmap).generate()
        val swatches = palette.swatches

        val primaryColors = swatches
            .sortedByDescending { it.population }
            .take(3)

        return@withContext primaryColors.map { it.rgb.toComposeColor() }
    }

    private fun Int.toComposeColor(): Color {
        return Color(android.graphics.Color.rgb(this shr 16 and 0xFF, this shr 8 and 0xFF, this and 0xFF))
    }
}