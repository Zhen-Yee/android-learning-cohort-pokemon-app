package com.expedia.androidleaningcohorttemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.expedia.androidleaningcohorttemplate.navigation.PokemonNavigation
import com.expedia.androidleaningcohorttemplate.ui.theme.AndroidLeaningCohortTemplateTheme
import com.expedia.androidleaningcohorttemplate.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pokemonViewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonViewModel.getPokemons()
        setContent {
            AndroidLeaningCohortTemplateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PokemonNavigation(viewModel = pokemonViewModel)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AndroidLeaningCohortTemplateTheme {
//        PokemonScreen(PokemonViewModel())
//    }
//}