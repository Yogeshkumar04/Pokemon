# Pokémon Application

## Overview

This Pokémon Application is built using Kotlin and Jetpack Compose. It utilizes the [PokéAPI] to fetch data about various Pokémon and displays it in a user-friendly interface. 
The app consists of two main screens: the Pokémon List Screen and the Pokémon Detail Screen.

## Table of Contents

- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Pokémon List Screen](#pokémon-list-screen)
- [Pokémon Detail Screen](#pokémon-detail-screen)
- [Changing the App Icon](#changing-the-app-icon)
- [Dependencies](#dependencies)

The MVVM (Model-View-ViewModel) pattern. Here's how each component fits into this architecture:

Model:

Model Directory: Contains data classes such as Pokemon, PokemonDetail, PokemonListResponse, etc. These classes represent the data structures used throughout the app and often correspond to the API responses.
View:

UI Directory: Contains Composables such as PokemonDetailScreen.kt and PokemonListScreen.kt. These files define the UI components and layout of the application.
ViewModel:

ViewModel Directory: Contains classes like PokemonDetailViewModel.kt and PokemonListViewModel.kt. These classes manage the data for the UI, handle business logic, and expose data via StateFlow or other observable mechanisms.
Repository:

Repository Directory: Contains PokemonRepository.kt. This class abstracts the data source, providing a clean API for data access to the rest of the application. It handles data operations and interacts with the network layer to fetch data.
Network:

Network Directory: Contains network-related classes like ApiClient.kt and PokeApiService.kt. These classes are responsible for making network requests and interacting with the API.
Navigation:

Navigation Directory: Contains NavGraph.kt, which defines the navigation graph and manages the navigation flow between different screens in the app.

