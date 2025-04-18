package com.purkt.mindexpense.features.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.purkt.mindexpense.features.profile.ui.ProfileScreen
import kotlinx.serialization.Serializable

/**
 * Route for Profile screen.
 */
@Serializable
internal data object ProfileRoute

/**
 * Route for Profile graph..
 */
@Serializable
data object ProfileGraphRoute

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(route = ProfileRoute, navOptions = navOptions)
}

fun NavGraphBuilder.profileGraph() {
    navigation<ProfileGraphRoute>(startDestination = ProfileRoute) {
        composable<ProfileRoute> {
            ProfileScreen()
        }
    }
}