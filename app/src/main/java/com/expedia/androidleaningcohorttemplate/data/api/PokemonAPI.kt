package com.expedia.androidleaningcohorttemplate.data.api

import com.expedia.androidleaningcohorttemplate.data.model.PokemonDTO
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDetailsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon")
    fun getPokemons(
        @Query("limit") limit: Int? = 0,
        @Query("offset") offset: Int? = 20
    ): Call<PokemonDTO>

    @GET("/pokemon/{name}")
    fun getPokemon(
        @Path("name") name: String
    ): Call<PokemonDetailsDTO>
}
