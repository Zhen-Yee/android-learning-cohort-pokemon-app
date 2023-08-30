package com.expedia.androidleaningcohorttemplate.viewmodel

import android.graphics.BitmapFactory
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonService: PokemonService
): ViewModel() {

    var pokemonList = mutableStateOf<List<Pokemon>>(listOf())
    var currentPage = 1
    val currentOffset = DEFAULT_OFFSET
    var pokemonDetailsMap = mutableStateOf(mutableMapOf<String, PokemonDetailsDTO>())
    var pokemonGradientMap = mutableStateOf(mutableMapOf<String, List<Color>>())
    var isLoading = mutableStateOf(false)

    fun getPokemons(limit: Int? = DEFAULT_LIMIT, offset: Int? = DEFAULT_OFFSET) {
        viewModelScope.launch(Dispatchers.Default) {
            isLoading.value = true
            val pokemons = pokemonService.getPokemons(limit, offset)?.toPokemons()
            if (pokemons != null) {
                pokemonList.value = pokemons
            } else {
                throw Exception("Error while fetching pokemons")
            }
            isLoading.value = false
        }
    }

    fun getNextPokemons() {
        viewModelScope.launch(Dispatchers.Default) {
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
        }
    }

    fun getPokemonDetailsById(id: String) {
        if (!pokemonDetailsMap.value.contains(id)) {
            viewModelScope.launch(Dispatchers.Default) {
                isLoading.value = true
                val pokemonDetails = pokemonService.getPokemonDetailsById(id)
                val pokemonGradient = extractPrimaryColorsFromImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png")

                if (pokemonDetails != null) {
                    pokemonDetailsMap.value[id] = pokemonDetails
                    pokemonGradientMap.value[id] = pokemonGradient
                    isLoading.value = false
                } else {
                    throw Exception("Error while fetching pokemon details")
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

    fun Int.toComposeColor(): Color {
        return Color(android.graphics.Color.rgb(this shr 16 and 0xFF, this shr 8 and 0xFF, this and 0xFF))
    }
}