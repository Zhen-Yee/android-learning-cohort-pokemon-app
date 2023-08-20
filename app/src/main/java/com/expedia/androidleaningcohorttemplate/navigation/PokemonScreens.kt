package com.expedia.androidleaningcohorttemplate.navigation

enum class PokemonScreens {
    PokemonScreen,
    PokemonDetailsScreen;

    companion object {
        fun fromRoute(route: String?): PokemonScreens =
            when(route?.substringBefore("/")) {
                PokemonScreen.name -> PokemonScreen
                PokemonDetailsScreen.name -> PokemonDetailsScreen
                null -> PokemonScreen
                else -> throw IllegalArgumentException("Route $route is not recognize")
            }
    }
}