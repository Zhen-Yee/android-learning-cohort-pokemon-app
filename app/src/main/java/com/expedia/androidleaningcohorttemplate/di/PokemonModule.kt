package com.expedia.androidleaningcohorttemplate.di

import com.expedia.androidleaningcohorttemplate.data.api.PokemonAPI
import com.expedia.androidleaningcohorttemplate.data.api.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    @Singleton
    @Provides
    fun providePokemonService(pokemonApi: PokemonAPI) = PokemonService(pokemonApi)

    @Singleton
    @Provides
    fun pokemonApi(): PokemonAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonAPI::class.java)
    }
}