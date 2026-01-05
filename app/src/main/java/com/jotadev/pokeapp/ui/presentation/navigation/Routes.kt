package com.jotadev.pokeapp.ui.presentation.navigation

import com.jotadev.pokeapp.R

sealed class Routes(
    val title: String,
    val iconOutlined: Int,
    val iconFilled: Int,
    val route: String
) {
    object HomeScreen: Routes(
        "Inicio",
        R.drawable.icon_home_outlined,
        R.drawable.icon_home_filled,
        "Inicio"
    )
    object FavoriteScreen: Routes(
        "Favoritos",
        R.drawable.icon_favorite_outlined,
        R.drawable.icon_favorite_filled,
        "Favoritos"
    )
    object SettingsScreen: Routes(
        "Configuracion",
        R.drawable.icon_settings_outlined,
        R.drawable.icon_settings_filled,
        "Configuracion"
    )
}