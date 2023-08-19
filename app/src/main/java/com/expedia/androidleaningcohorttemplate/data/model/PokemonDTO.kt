package com.expedia.androidleaningcohorttemplate.data.model

data class PokemonDTO(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<PokemonNameDTO>
)

data class PokemonNameDTO(
    val name: String,
    val url: String
)
