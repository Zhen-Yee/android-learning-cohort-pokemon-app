package com.expedia.androidleaningcohorttemplate.navigation

import PokemonDetailsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

        composable(
            route = "${PokemonScreens.PokemonDetailsScreen.name}/{pokemonId}",
            arguments = listOf(navArgument(name = "pokemonId") { type = NavType.StringType})
        ) { backStackEntry ->
            PokemonDetailsScreen(
                viewModel = viewModel,
                navController = navController,
                pokemonId = backStackEntry.arguments?.getString("pokemonId")!!
            )
        }
    }
}