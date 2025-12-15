package com.royaldeckhub.app.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.royaldeckhub.app.data.repository.MockRepository
import com.royaldeckhub.app.ui.navigation.Screen
import com.royaldeckhub.app.ui.screens.AboutScreen
import com.royaldeckhub.app.ui.screens.DetailFavScreen
import com.royaldeckhub.app.ui.screens.DetailItemScreen
import com.royaldeckhub.app.ui.screens.ElemListScreen
import com.royaldeckhub.app.ui.screens.FavListScreen
import com.royaldeckhub.app.ui.screens.ProfileScreen
import com.royaldeckhub.app.ui.components.RoyalAppBar
import com.royaldeckhub.app.ui.theme.RoyalGold
import com.royaldeckhub.app.ui.theme.RoyalBlue

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    // Adaptive Layout Logic
    BoxWithConstraints {
        val isExpanded = maxWidth > 600.dp
        val navItems = listOf(Screen.Home, Screen.Favorites, Screen.Profile, Screen.About)
        
        if (isExpanded) {
            // Tablet/Expanded Layout: Navigation Rail
            Row(modifier = Modifier.fillMaxSize()) {
                RoyalNavRail(navController, navItems)
                
                Scaffold(
                    topBar = {
                       // Optional: Specific TopBar per screen or global
                       RoyalTopBarWrapper(navController)
                    }
                ) { padding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(padding),
                        isExpanded = true
                    )
                }
            }
        } else {
            // Mobile/Compact Layout: Bottom Navigation
            Scaffold(
                topBar = { RoyalTopBarWrapper(navController) },
                bottomBar = { RoyalBottomBar(navController, navItems) }
            ) { padding ->
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(padding),
                    isExpanded = false
                )
            }
        }
    }
}

@Composable
fun RoyalTopBarWrapper(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Determine title and up navigation availability
    val title = when {
        currentRoute == Screen.Home.route -> Screen.Home.title
        currentRoute == Screen.Favorites.route -> Screen.Favorites.title
        currentRoute == Screen.Profile.route -> Screen.Profile.title
        currentRoute == Screen.About.route -> Screen.About.title
        currentRoute?.startsWith("detail") == true -> "Card Details"
        currentRoute?.startsWith("fav_detail") == true -> "Favorite Details"
        else -> "RoyalDeckHub"
    }

    val canNavigateBack = navController.previousBackStackEntry != null
    
    RoyalAppBar(
        title = title,
        canNavigateBack = canNavigateBack,
        navigateUp = { navController.navigateUp() }
    )
}

@Composable
fun RoyalBottomBar(navController: NavHostController, items: List<Screen>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = RoyalBlue, // Custom Theme Color
    ) {
        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                icon = { screen.icon?.let { Icon(it, contentDescription = screen.title) } },
                label = { Text(screen.title) },
                selected = isSelected,
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
                    selectedIconColor = RoyalBlue,
                    selectedTextColor = RoyalGold,
                    indicatorColor = RoyalGold,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray
                )
            )
        }
    }
}

@Composable
fun RoyalNavRail(navController: NavHostController, items: List<Screen>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationRail(
        containerColor = RoyalBlue,
    ) {
        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationRailItem(
                icon = { screen.icon?.let { Icon(it, contentDescription = screen.title) } },
                label = { Text(screen.title) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = RoyalBlue,
                    selectedTextColor = RoyalGold,
                    indicatorColor = RoyalGold,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray
                )
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isExpanded: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
            composable(Screen.Home.route) {
                ElemListScreen(
                    elements = MockRepository.elements,
                    favoriteIds = MockRepository.favoriteIds,
                    onElementClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                    onFavClick = { id -> MockRepository.toggleFavorite(id) },
                    isExpanded = isExpanded
                )
            }
            
            composable(Screen.Detail.route) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("elementId")?.toIntOrNull()
                val element = id?.let { MockRepository.getElement(it) }
                
                if (element != null) {
                    DetailItemScreen(
                        element = element,
                        isFavorite = MockRepository.isFavorite(element.id),
                        onFavClick = { MockRepository.toggleFavorite(element.id) },
                        isExpanded = isExpanded
                    )
                } else {
                    Text("Details not found")
                }
            }
            
            composable(Screen.Favorites.route) {
                FavListScreen(
                    elements = MockRepository.elements,
                    favoriteIds = MockRepository.favoriteIds,
                    onElementClick = { id -> navController.navigate(Screen.FavDetail.createRoute(id)) }, // Go to FavDetail
                    onRemoveFav = { id -> MockRepository.toggleFavorite(id) },
                    isExpanded = isExpanded
                )
            }
            
            composable(Screen.FavDetail.route) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("elementId")?.toIntOrNull()
                val element = id?.let { MockRepository.getElement(it) }

                if (element != null) {
                    DetailFavScreen(
                        element = element,
                        comments = MockRepository.getCommentsFor(element.id),
                        onAddComment = { MockRepository.addComment(element.id, "This deck rocks!", "Me") }
                    )
                } else {
                    Text("Details not found")
                }
            }
            
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            
            composable(Screen.About.route) {
                AboutScreen()
            }
    }
}
