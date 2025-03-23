package com.purkt.mindexpress.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.purkt.mindexpress.profile.ProfileScreen
import kotlinx.serialization.Serializable

/**
 * Route for Profile screen.
 */
@Serializable
data object ProfileRoute

/**
 * Route for Profile graph..
 */
@Serializable
data object ProfileGraphRoute

fun NavController.navigateToProfile(navOptions: NavOptions) {
    navigate(route = ProfileRoute, navOptions = navOptions)
}

fun NavGraphBuilder.profileGraph() {
    navigation<ProfileGraphRoute>(startDestination = ProfileRoute) {
        composable<ProfileRoute> {
            ProfileScreen()
        }
    }
}