package com.example.pokemonapp.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.pokemonapp.network.ApiClient
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.viewmodel.PokemonDetailViewModel
import com.example.pokemonapp.viewmodel.PokemonDetailViewModelFactory

@Composable
fun PokemonDetailScreen(pokemonId: Int) {
    val repository = remember { PokemonRepository(ApiClient.apiService) }
    val viewModel: PokemonDetailViewModel = viewModel(
        factory = PokemonDetailViewModelFactory(repository)
    )

    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.fetchPokemonDetail(pokemonId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pokemon's Details",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)

            )

            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.Black,
                thickness = 2.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage != null) {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else {
                pokemonDetail?.let { detail ->
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Name: ${detail.name.capitalize()}",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 28.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    Image(
                        painter = rememberAsyncImagePainter(detail.sprites.front_default),
                        contentDescription = null,
                        modifier = Modifier
                            .size(250.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Red)
                            .padding(8.dp)
                            .shadow(4.dp, RoundedCornerShape(8.dp))
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Black,
                        thickness = 2.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    Text(
                        text = "Height: ${detail.height}",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 28.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Weight: ${detail.weight}",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 28.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace
                    )



                    Spacer(modifier = Modifier.height(16.dp))


                    detail.types.forEach { type ->
                        Text(
                            text = "Type: ${type.type.name.capitalize()}",
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 28.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        color = Color.Black,
                        thickness = 2.dp)

                    Spacer(modifier = Modifier.height(16.dp))
                } ?: Text(
                    text = "Loading details...",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    PokemonDetailScreen(pokemonId = 1)
}
