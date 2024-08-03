package dev.krishna.imagecolourextractor.navigation

sealed class Screens(val route: String) {
    object Home: Screens("Screen1")
    object Details: Screens("details_screen_route")
    object CameraScreen: Screens("camera_screen")
}
