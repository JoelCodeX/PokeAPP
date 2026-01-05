package com.jotadev.pokeapp.ui.presentation.screens.favorite

import android.text.TextUtils.isEmpty
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jotadev.pokeapp.ui.presentation.screens.components.PokemonCard
import com.jotadev.pokeapp.ui.presentation.screens.components.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController

) {
    Scaffold(
        topBar = {
            TopAppBar(title = "FAVORITOS")
        },
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            val favorites = emptyList<String>() // Placeholder
            if (favorites.isEmpty()) {
                Text(
                    text = "No has añadido ningún Pokémon a favoritos",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                )
            } else {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    items(favorites.size) {
//                         // PokemonCard(navController = navController, pokemon = ...)
//                    }
//                }
            }
        }
    }

}
