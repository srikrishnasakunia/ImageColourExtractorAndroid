package dev.krishna.imagecolourextractor.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.krishna.imagecolourextractor.presentation.DetailsScreen
import dev.krishna.imagecolourextractor.presentation.HomeScreen
import dev.krishna.imagecolourextractor.presentation.camera.CameraScreen

@Composable
fun MyNavGraph(
    navController: NavHostController,
    applicationContext: Context
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen (
                moveToDetails = { navController.navigate(Screens.Details.route) }
            )
        }
        composable(Screens.Details.route) {
            DetailsScreen (
                moveToCamera = { navController.navigate(Screens.CameraScreen.route) }
            )
        }
        composable(Screens.CameraScreen.route) {
            CameraScreen (
                applicationContext = applicationContext
            )
        }
    }
}