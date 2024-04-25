package dev.krishna.imagecolourextractor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.krishna.imagecolourextractor.presentation.DetailsScreen
import dev.krishna.imagecolourextractor.presentation.HomeScreen

@Composable
fun MyNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Screen1") {
        composable("Screen1") {
            HomeScreen(navController)
        }
        composable("details_screen_route") {
            DetailsScreen(navController = navController)
        }
    }
    
}