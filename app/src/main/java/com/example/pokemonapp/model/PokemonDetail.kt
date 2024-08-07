package com.example.pokemonapp.model

data class PokemonDetail(
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<TypeSlot>
)
