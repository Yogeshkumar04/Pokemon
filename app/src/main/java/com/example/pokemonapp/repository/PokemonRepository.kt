package com.example.pokemonapp.repository

import com.example.pokemonapp.network.PokeApiService


class PokemonRepository(private val apiService: PokeApiService) {
    suspend fun getPokemonList(offset: Int, limit: Int) = apiService.getPokemonList(offset, limit)
    suspend fun getPokemonDetail(id: Int) = apiService.getPokemonDetail(id)


}
