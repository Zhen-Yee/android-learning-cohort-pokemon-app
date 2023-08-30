package com.expedia.androidleaningcohorttemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.expedia.androidleaningcohorttemplate.R
import com.expedia.androidleaningcohorttemplate.data.model.Pokemon
import com.expedia.androidleaningcohorttemplate.navigation.PokemonScreens
import com.expedia.androidleaningcohorttemplate.util.TOTAL_POKEMON_COUNT
import com.expedia.androidleaningcohorttemplate.util.capitalize
import com.expedia.androidleaningcohorttemplate.util.formatOrderId
import com.expedia.androidleaningcohorttemplate.viewmodel.PokemonViewModel

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel,
    navController: NavController
) {
    val gradientColors = listOf(
        Color(0xFFFF0000),
        Color(0xFF000000),
        Color(0xFFFFFFFF)
    )

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val pokemons by remember { viewModel.pokemonList }
        val isLoading by remember { viewModel.isLoading }
        val scrollState = rememberLazyListState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(gradientColors)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pokemon_logo),
                    contentDescription = "Pokemon Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .align(CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isLoading) {
                    LoadingScreen()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = scrollState
                    ) {
                        itemsIndexed(pokemons) { index, pokemon ->
                            if (index >= pokemons.size - 1 && index <= TOTAL_POKEMON_COUNT) {
                                viewModel.getNextPokemons()
                            }
                            PokemonCard(
                                viewModel = viewModel,
                                pokemon = pokemon,
                                navController = navController
                            )
                            Spacer(modifier = Modifier.height(16.dp)) // Add spacing between cards
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun PokemonCard(
    viewModel: PokemonViewModel,
    pokemon: Pokemon,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .shadow(5.dp, RoundedCornerShape(50.dp))
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White)
            .aspectRatio(1f)
            .clickable {
                viewModel.getPokemonDetailsById(pokemon.id)
                navController.navigate("${PokemonScreens.PokemonDetailsScreen.name}/${pokemon.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "#${pokemon.id.formatOrderId()}",
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrl),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(180.dp)
                    .align(CenterHorizontally)
            )
            Text(
                text = pokemon.name.capitalize(),
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
