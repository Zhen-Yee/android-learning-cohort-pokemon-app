package com.expedia.androidleaningcohorttemplate.config

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://pokeapi.co/api/v2/pokemon/"

    fun getClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
}