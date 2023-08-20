package com.expedia.androidleaningcohorttemplate.util

import com.expedia.androidleaningcohorttemplate.data.model.Pokemon
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDTO
import com.expedia.androidleaningcohorttemplate.data.model.PokemonNameDTO

fun PokemonDTO.toPokemons(): List<Pokemon> {
    return this.results.map {
        val pokemonId = it.extractPokemonId()
        Pokemon(
            id = pokemonId,
            name = it.name,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png"
        )
    }
}

fun PokemonNameDTO.extractPokemonId(): String {
    val arr = this.url.dropLast(1).split("/")
    return arr[arr.size - 1]
}

fun String.capitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase()
        else
            it.toString()
    }
}