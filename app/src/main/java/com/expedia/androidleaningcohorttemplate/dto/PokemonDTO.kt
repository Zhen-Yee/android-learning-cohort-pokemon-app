package com.expedia.androidleaningcohorttemplate.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PokemonDTO(
    @JsonProperty("next") val next: String,
    @JsonProperty("previous") val previous: String?,
    @JsonProperty("results") val results: List<PokemonNameDTO>
)

data class PokemonNameDTO(
    @JsonProperty("name") val name: String,
    @JsonProperty("url") val url: String
)