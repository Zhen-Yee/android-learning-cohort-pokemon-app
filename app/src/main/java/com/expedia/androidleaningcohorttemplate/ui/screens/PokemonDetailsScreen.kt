import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.expedia.androidleaningcohorttemplate.util.capitalize
import com.expedia.androidleaningcohorttemplate.viewmodel.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonDetailsScreen(
    viewModel: PokemonViewModel,
    navController: NavController,
    pokemonId: String
) {
    val pokemons by remember { viewModel.pokemonDetailsMap }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Pokemon Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Arrow")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Add more details about the Pokemon here
                Image(
                    painter = rememberAsyncImagePainter(model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png"),
                    contentDescription = pokemons[pokemonId]?.name,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
                pokemons[pokemonId]?.name?.capitalize()?.let { it1 ->
                    Text(
                        text = it1,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(
                    text = "Order #${pokemons[pokemonId]?.order}",
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Height: ${pokemons[pokemonId]?.height}",
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Weight: ${pokemons[pokemonId]?.weight}",
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
