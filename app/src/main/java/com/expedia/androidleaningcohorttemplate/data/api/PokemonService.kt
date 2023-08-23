package com.expedia.androidleaningcohorttemplate.data.api

import android.util.Log
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDTO
import com.expedia.androidleaningcohorttemplate.data.model.PokemonDetailsDTO
import com.expedia.androidleaningcohorttemplate.util.DEFAULT_LIMIT
import com.expedia.androidleaningcohorttemplate.util.DEFAULT_OFFSET
import com.google.gson.Gson
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonService @Inject constructor(
    private val pokemonAPI: PokemonAPI
) {
    suspend fun getPokemons(limit: Int? = DEFAULT_LIMIT, offset: Int? = DEFAULT_OFFSET): PokemonDTO? {
        return try {
            val response = pokemonAPI.getPokemons(limit, offset).execute().body()
            Log.d("PokemonService", Gson().toJson(response))
            response
        } catch (e: Exception) {
            Log.d("PokemonService", "Failed: $e")
            null
        }
    }

    suspend fun getPokemonDetailsById(id: String): PokemonDetailsDTO? {
        return try {
            val response = pokemonAPI.getPokemon(id).execute().body()
            Log.d("PokemonService", Gson().toJson(response))
            response
        } catch (e: Exception) {
            Log.d("PokemonService", "Failed: $e")
            null
        }
    }
}
