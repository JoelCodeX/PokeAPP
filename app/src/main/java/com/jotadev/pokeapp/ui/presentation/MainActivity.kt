package com.jotadev.pokeapp.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jotadev.pokeapp.ui.presentation.navigation.Routes
import com.jotadev.pokeapp.ui.presentation.screens.details.DetailsScreen
import com.jotadev.pokeapp.ui.presentation.screens.home.HomeScreen
import com.jotadev.pokeapp.ui.presentation.screens.favorite.FavoriteScreen
import com.jotadev.pokeapp.ui.presentation.screens.settings.SettingsScreen
import com.jotadev.pokeapp.ui.presentation.theme.PokeAPPTheme
import com.jotadev.pokeapp.ui.presentation.theme.orange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        false.setupStatusBar()

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) } // Estado del tema
            PokeAPPTheme(darkTheme = isDarkTheme) { // Pasar estado a PokeAPPTheme
                MainScreen(
                    isDarkTheme = isDarkTheme,
                    onDarkThemeToggle = { isDarkTheme = it } // Callback para cambiar el tema
                )
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun Boolean.setupStatusBar() {
        this@MainActivity.window.statusBarColor = orange.toArgb()
        WindowInsetsControllerCompat(
            this@MainActivity.window,
            this@MainActivity.window.decorView
        ).isAppearanceLightStatusBars =
            this
    }

    @Composable
    fun MainScreen(isDarkTheme: Boolean, onDarkThemeToggle: (Boolean) -> Unit) {
        val navController = rememberNavController()
        val screens = listOf(Routes.HomeScreen, Routes.FavoriteScreen, Routes.SettingsScreen)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        var showLogoutDialog by remember { mutableStateOf(false) }

        val selectedColor = orange
        val unselectedColor = if (isDarkTheme) Color.White.copy(alpha = 0.6f) else Color.Black.copy(alpha = 0.6f)

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.HomeScreen.route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(Routes.HomeScreen.route) {
                        HomeScreen(navController)
                    }
                    composable(Routes.FavoriteScreen.route) {
                        FavoriteScreen(navController)
                    }
                    composable(Routes.SettingsScreen.route) {
                        SettingsScreen(
                            isDarkModeEnabled = isDarkTheme,
                            onDarkModeToggle = onDarkThemeToggle,
                            onLogout = { showLogoutDialog = true }
                        )
                    }
                    composable(
                        route = "details/{name}",
                        arguments = listOf(navArgument("name") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name") ?: ""
                        DetailsScreen(
                            name = name,
                            onBackClick = { navController.popBackStack() },
                            onFavoriteClick = { /* Handle favorite click */ }
                        )
                    }
                }
                if (screens.any{it.route==currentRoute}){
                    NavigationBar(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(18.dp)),
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 200.dp
                    ) {
                        screens.forEach { screen ->
                            val selected = currentRoute == screen.route
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(
                                            id = if (selected) screen.iconFilled else screen.iconOutlined
                                        ),
                                        contentDescription = screen.title,
                                        modifier = Modifier.scale(if (selected) 1.4f else 1f),
                                        tint = if (selected) selectedColor else unselectedColor
                                    )
                                },
                                label = {
                                    Text(
                                        text = screen.title,
                                        fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Normal,
                                        modifier = Modifier.scale(if (selected) 1.1f else 1f),
                                        color = if (selected) selectedColor else unselectedColor
                                    )
                                },
                                selected = selected,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = selectedColor,
                                    unselectedIconColor = unselectedColor,
                                    selectedTextColor = selectedColor,
                                    unselectedTextColor = unselectedColor,
                                    indicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}