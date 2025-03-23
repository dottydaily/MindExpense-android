package com.purkt.mindexpense.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.purkt.mindexpense.home.HomeScreen
import kotlinx.serialization.Serializable

/**
 * Route for Home screen.
 */
@Serializable
data object HomeRoute

/**
 * Route for Home graph.
 */
@Serializable
data object HomeGraphRoute

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(route = HomeRoute, navOptions = navOptions)
}

fun NavGraphBuilder.homeGraph() {
    navigation<HomeGraphRoute>(startDestination = HomeRoute) {
        composable<HomeRoute>{
            HomeScreen()
        }
    }
}