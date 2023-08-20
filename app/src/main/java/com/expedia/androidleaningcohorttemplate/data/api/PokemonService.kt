package com.expedia.androidleaningcohorttemplate.data.api

import android.util.Log
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDTO
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDetailsDTO
import com.google.gson.Gson
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonService @Inject constructor(
    private val pokemonAPI: PokemonAPI
) {
    suspend fun getPokemons(): PokemonDTO? {
        return try {
            val response = pokemonAPI.getPokemons().execute().body()
            Log.d("PokemonService", Gson().toJson(response))
            response
        } catch (e: Exception) {
            Log.d("PokemonService", "Failed: $e")
            null
        }
    }

    suspend fun getPokemon(name: String): PokemonDetailsDTO? {
        return try {
            val response = pokemonAPI.getPokemon(name).execute().body()
            Log.d("PokemonService", Gson().toJson(response))
            response
        } catch (e: Exception) {
            Log.d("PokemonService", "Failed: $e")
            null
        }
    }
}
