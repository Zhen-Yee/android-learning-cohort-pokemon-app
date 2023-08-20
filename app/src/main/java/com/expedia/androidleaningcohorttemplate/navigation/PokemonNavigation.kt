package com.expedia.androidleaningcohorttemplate.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
            // todo
            Surface {
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black,
                                    Color.Transparent
                                )
                            )
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(36.dp)
                            .offset(16.dp, 16.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Text(
                        text = "On the details screens. Todo.",
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}