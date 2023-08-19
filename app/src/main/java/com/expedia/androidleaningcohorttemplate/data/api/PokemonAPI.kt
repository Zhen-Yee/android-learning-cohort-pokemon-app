package com.expedia.androidleaningcohorttemplate.data.api

import com.expedia.androidleaningcohorttemplate.data.model.PokemonDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon")
    fun getPokemons(
        @Query("limit") limit: Int? = 0,
        @Query("offset") offset: Int? = 20
    ): Call<PokemonDTO>
}
