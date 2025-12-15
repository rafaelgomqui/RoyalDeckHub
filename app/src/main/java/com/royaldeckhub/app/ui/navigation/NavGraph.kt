package com.royaldeckhub.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Detail : Screen("detail/{elementId}", "Detail") {
        fun createRoute(elementId: Int) = "detail/$elementId"
    }
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    object FavDetail : Screen("fav_detail/{elementId}", "Fav Detail") {
        fun createRoute(elementId: Int) = "fav_detail/$elementId"
    }
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object About : Screen("about", "About", Icons.Default.Info)
}
