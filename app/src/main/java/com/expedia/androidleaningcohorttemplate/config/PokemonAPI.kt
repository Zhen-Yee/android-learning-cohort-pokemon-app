package com.expedia.androidleaningcohorttemplate.config

import com.expedia.androidleaningcohorttemplate.dto.PokemonDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface PokemonAPI {

    @GET("?offset=0&limit=20")
    fun getPokemons(): Call<PokemonDTO>
}