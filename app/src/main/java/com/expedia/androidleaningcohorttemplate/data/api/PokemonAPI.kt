package com.expedia.androidleaningcohorttemplate.data.api

import com.expedia.androidleaningcohorttemplate.data.model.PokemonDTO
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDetailsDTO
import com.expedia.androidleaningcohorttemplate.util.DEFAULT_LIMIT
import com.expedia.androidleaningcohorttemplate.util.DEFAULT_OFFSET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int? = DEFAULT_LIMIT,
        @Query("offset") offset: Int? = DEFAULT_OFFSET
    ): Response<PokemonDTO>

    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: String
    ): Response<PokemonDetailsDTO>
}
