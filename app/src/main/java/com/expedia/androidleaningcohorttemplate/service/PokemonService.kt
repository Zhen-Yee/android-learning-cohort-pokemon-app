package com.expedia.androidleaningcohorttemplate.service

import android.util.Log
import com.expedia.androidleaningcohorttemplate.config.PokemonAPI
import com.expedia.androidleaningcohorttemplate.config.RetrofitClient
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PokemonService {

    private val retrofit = RetrofitClient.getClient()
    private val pokemonAPI = retrofit.create(PokemonAPI::class.java)

    fun getPokemons() {
        val mapper = ObjectMapper()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = pokemonAPI.getPokemons().execute()
                val pokemons = response.body()
                Log.d("PokemonService", mapper.writeValueAsString(pokemons))
            } catch (e: Exception) {
                Log.d("PokemonService", "Faliedz $e")
            }
        }
    }
}