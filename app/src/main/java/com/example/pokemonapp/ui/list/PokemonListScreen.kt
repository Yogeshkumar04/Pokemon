package com.example.pokemonapp.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pokemonapp.network.ApiClient
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.viewmodel.PokemonListViewModel
import com.example.pokemonapp.viewmodel.PokemonListViewModelFactory


@Composable
fun PokemonListScreen(navController: NavController) {
    val repository = remember { PokemonRepository(ApiClient.apiService) }
    val viewModel: PokemonListViewModel = viewModel(
        factory = PokemonListViewModelFactory(repository)
    )

    val pokemonList by viewModel.pokemonList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Transparent)
                    )
                )
                .padding(vertical = 16.dp)
        ) {
            Column(modifier = Modifier
                .padding(18.dp)
                .background(Color.Transparent)) {
                // Title of the screen
                Text(
                    text = "Pokemon's List",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else if (errorMessage != null) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: $errorMessage",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = { viewModel.loadPokemonList() }) {
                            Text(text = "Retry")
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .weight(1f)
                            .background(Color.Transparent)
                    ) {
                        items(pokemonList) { pokemon ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        val pokemonId = pokemon.url
                                            .split("/")
                                            .last { it.isNotEmpty() }
                                            .toInt()
                                        navController.navigate("pokemon_detail/$pokemonId")
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White // Set the desired background color here
                                ),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Row(modifier = Modifier.padding(16.dp)) {
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column {
                                        Text(
                                            text = pokemon.name.capitalize(),
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = FontFamily.Monospace,
                                            color = Color.Black,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { viewModel.fetchPreviousPage() },
                            enabled = viewModel.offset >= viewModel.limit
                        ) {
                            Text("Previous")
                            Spacer(modifier = Modifier.width(8.dp))

                        }

                        Card(
                            modifier = Modifier
                                .padding(8.dp),

                            shape = MaterialTheme.shapes.medium,

                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary // Use theme's primary color
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ){
                            Text(text = "${if (viewModel.offset / viewModel.limit == 0) 1 else (viewModel.offset / viewModel.limit) + 1}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }



                        Button(onClick = { viewModel.fetchNextPage() }) {
                            Text("Next")
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadPokemonList()
    }
}