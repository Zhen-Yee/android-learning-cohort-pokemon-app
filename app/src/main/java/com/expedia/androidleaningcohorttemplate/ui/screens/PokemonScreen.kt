package com.expedia.androidleaningcohorttemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.expedia.androidleaningcohorttemplate.data.model.Pokemon
import com.expedia.androidleaningcohorttemplate.navigation.PokemonScreens
import com.expedia.androidleaningcohorttemplate.util.TOTAL_POKEMON_COUNT
import com.expedia.androidleaningcohorttemplate.util.capitalize
import com.expedia.androidleaningcohorttemplate.viewmodel.PokemonViewModel

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel,
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val pokemons by remember { viewModel.pokemonList }

        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                itemsIndexed(pokemons) { index, pokemon ->
                    if (index >= pokemons.size - 1 && index <= TOTAL_POKEMON_COUNT) {
                        println(index)
                        viewModel.getNextPokemons()
                    }
                    PokemonCard(pokemon = pokemon, navController = navController)
                }
            }
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .clickable {
                navController.navigate(PokemonScreens.PokemonDetailsScreen.name)
                // todo navController
                // navigate to details screen
            }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrl),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
            )
            Text(
                text = pokemon.name.capitalize(),
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "#${pokemon.id}",
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}