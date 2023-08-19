package com.expedia.androidleaningcohorttemplate.data.api

import android.util.Log
import com.google.gson.Gson
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonService @Inject constructor(
    private val pokemonAPI: PokemonAPI
) {
    suspend fun getPokemons() {
        try {
            val response = pokemonAPI.getPokemons().execute().body()
            Log.d("PokemonService", Gson().toJson(response))
        } catch (e: Exception) {
            Log.d("PokemonService", "Failed: $e")
        }
    }
}
