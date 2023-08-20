package com.expedia.androidleaningcohorttemplate.navigation

import PokemonDetailsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.expedia.androidleaningcohorttemplate.ui.screens.PokemonScreen
import com.expedia.androidleaningcohorttemplate.viewmodel.PokemonViewModel

@Composable
fun PokemonNavigation(viewModel: PokemonViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PokemonScreens.PokemonScreen.name
    ) {
        composable(PokemonScreens.PokemonScreen.name) {
            PokemonScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(PokemonScreens.PokemonDetailsScreen.name) {
            PokemonDetailsScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}